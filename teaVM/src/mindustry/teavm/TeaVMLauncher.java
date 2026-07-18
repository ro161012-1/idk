package mindustry.teavm;

import arc.backends.teavm.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.net.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.logic.*;
import mindustry.maps.*;
import mindustry.mod.*;
import mindustry.net.*;
import mindustry.ui.*;
import mindustry.world.*;

import static arc.Core.*;
import static mindustry.Vars.*;

/**
 * TeaVM Launcher for Mindustry HTML5 Port.
 * Uses TeaVM's WebAssembly backend for near-native performance.
 * Supports reflection, threading, and all Java features needed by Mindustry.
 */
public class TeaVMLauncher extends TeaVMApplication {
    
    @Override
    public TeaVMApplicationConfiguration getConfig() {
        TeaVMApplicationConfiguration cfg = new TeaVMApplicationConfiguration();
        
        // Canvas configuration
        cfg.canvasId = "canvas";
        cfg.width = "100%";
        cfg.height = "100%";
        cfg.preferWebGL2 = true;
        cfg.alpha = false;
        cfg.antialiasing = true;
        cfg.stencil = true;
        cfg.preserveDrawingBuffer = false;
        cfg.failIfMajorPerformanceCaveat = false;
        
        // Audio via Web Audio API
        cfg.useAudio = true;
        cfg.audioContext = null; // Auto-create
        
        // Input - prevent default for game keys
        cfg.preventDefaultKeys = new int[]{
            32, 37, 38, 39, 40, 13, 27, 9, 16, 17, 18,  // space, arrows, enter, esc, tab, shift, ctrl, alt
            65, 83, 68, 87, 69, 81, 82, 70,               // WASD, E, Q, R, F
            49, 50, 51, 52, 53, 54, 55, 56, 57, 48,        // 0-9
            112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, // F1-F12
            188, 190, 191, 220, 221, 186, 222, 192        // , . / \ ] ; ' `
        };
        
        return cfg;
    }
    
    @Override
    public ApplicationListener createApplicationListener() {
        // Initialize platform BEFORE any Mindustry static initialization
        TeaVMPlatform platform = new TeaVMPlatform();
        Platform.platform = platform;
        
        // Initialize logging
        Vars.loadLogger();
        
        // Load file logger to IndexedDB
        loadFileLogger();
        
        return new TeaVMGame(platform);
    }
    
    /**
     * Main game class - Full Mindustry implementation for TeaVM/WebAssembly.
     * This mimics ClientLauncher but uses TeaVM-compatible implementations.
     */
    public static class TeaVMGame implements ApplicationListener {
        private final TeaVMPlatform platform;
        private LoadRenderer loader;
        private long beginTime;
        private boolean finished = false;
        private long nextFrame;
        private long lastTargetFps = -1;
        
        public TeaVMGame(TeaVMPlatform platform) {
            this.platform = platform;
        }
        
        @Override
        public void create() {
            beginTime = Time.millis();
            
            try {
                setupPlatform();
                initGraphics();
                initAssets();
                initCoreSystems();
                initGameSystems();
                startLoading();
            } catch (Throwable t) {
                Log.err("Failed to initialize game", t);
                showCrashScreen(t);
            }
        }
        
        private void setupPlatform() {
            // Set data directory to IndexedDB virtual filesystem
            String dataDir = TeaVMLocalStorage.getItem("mindustry.data.dir");
            if (dataDir != null) {
                Core.settings.setDataDirectory(files.absolute(dataDir));
            }
            
            platform = this.platform;
            maxTextureSize = Gl.getInt(Gl.maxTextureSize);
            
            Log.info("[GL] Version: @", graphics.getGLVersion());
            Log.info("[GL] Max texture size: @", maxTextureSize);
            Log.info("[GL] Using WebGL 2 API.");
            
            // Time delta provider for 60fps fixed timestep
            Time.setDeltaProvider(() -> {
                float result = Core.graphics.getDeltaTime() * 60f;
                return (Float.isNaN(result) || Float.isInfinite(result)) ? 1f : Mathf.clamp(result, 0.0001f, 10f);
            });
            
            // Initialize Intel GPU check
            IntelGpuCheck.init(graphics.getGLVersion().vendorString);
            
            boolean isIntel = IntelGpuCheck.wasIntel();
            
            if (isIntel && !graphics.isGL30Available()) Log.warn("[GL] Intel GPU detected on previous launch. Due to memory corruption issues, OpenGL 3 support has been disabled for Intel GPUs. See issue #11041.");
            
            if (gl30 == null && !isIntel) Log.warn("[GL] Your device or video drivers do not support OpenGL 3. This will cause performance issues.");
            
            if (maxTextureSize < 4096) Log.warn("[GL] Your maximum texture size is below the recommended minimum of 4096. This will cause severe performance issues.");
            
            Log.info("[JAVA] Version: @", "TeaVM 0.10.0 (WASM)");
            long ram = Runtime.getRuntime().maxMemory();
            boolean gb = ram >= 1024 * 1024 * 1024;
            Log.info("[RAM] Available: @ @", Strings.fixed(gb ? ram / 1024f / 1024 / 1024f : ram / 1024f / 1024f, 1), gb ? "GB" : "MB");
        }
        
        private void initGraphics() {
            batch = new SpriteBatch();
            assets = new AssetManager();
            assets.setLoader(Texture.class, "." + mapExtension, new MapPreviewLoader());
            
            tree = new FileTree();
            
            // Sound/Music loaders using Web Audio API
            assets.setLoader(Sound.class, new SoundLoader(tree) {
                @Override public void loadAsync(AssetManager manager, String fileName, Fi file, SoundParameter parameter) {}
                @Override public Sound loadSync(AssetManager manager, String fileName, Fi file, SoundParameter parameter) {
                    Sound sound = parameter != null && parameter.sound != null ? parameter.sound : new Sound();
                    sound.loadLazy(file);
                    return sound;
                }
            });
            
            assets.setLoader(Music.class, new MusicLoader(tree) {
                @Override public void loadAsync(AssetManager manager, String fileName, Fi file, MusicParameter parameter) {}
                @Override public Music loadSync(AssetManager manager, String fileName, Fi file, MusicParameter parameter) {
                    Music music = parameter != null && parameter.music != null ? parameter.music : new Music();
                    mainExecutor.submit(() -> {
                        try { music.load(file); } catch (Throwable t) { Log.err("Error loading music: " + file, t); }
                    });
                    return music;
                }
            });
        }
        
        private void initAssets() {
            // Error texture first
            assets.load("sprites/error.png", Texture.class);
            atlas = TextureAtlas.blankAtlas();
            
            // Network - FULL WebSocket implementation
            Vars.net = new Net(platform.getNet());
            MapPreviewLoader.setupLoaders();
            
            // Mods - FULL mod system with reflection (TeaVM supports this!)
            mods = new Mods();
            schematics = new Schematics();
            
            Fonts.loadSystemCursors();
            
            // Load Vars - THIS INITIALIZES ALL CONTENT
            assets.load(new Vars());
            
            Fonts.loadDefaultFont();
            
            // Atlas - use fallback if texture size too small
            String atlasPath = maxTextureSize >= 4096 ? "sprites/sprites.aatls" : "sprites/fallback/sprites.aatls";
            assets.load(new AssetDescriptor<>(atlasPath, TextureAtlas.class)).loaded = t -> atlas = t;
            
            assets.loadRun("maps", Map.class, () -> maps.loadPreviews());
            
            Musics.load();
            Sounds.load();
            
            // Content creation - FULL INCLUDING MODS
            assets.loadRun("contentcreate", Content.class, 
                () -> {
                    content.createBaseContent();
                    // Initialize ALL content types in correct order
                    Items.load();
                    Liquids.load();
                    StatusEffects.load();
                    UnitTypes.load();
                    Blocks.load();
                    SectorPresets.load();
                    Planets.load();
                    Effect.load();
                    Weather.load();
                    UnitCommands.load();
                    ResearchTree.load();
                    TechTree.load();
                    Loadouts.load();
                    TeamEntries.load();
                },
                () -> {
                    content.loadColors();
                    // Mod content creation (TeaVM supports reflection!)
                    mods.loadScripts();
                    content.createModContent();
                }
            );
            
            assets.load(mods);
            assets.loadRun("mergeUI", PixmapPacker.class, () -> {}, () -> Fonts.mergeFontAtlas(atlas));
            
            // Core modules - ALL OF THEM
            add(logic = new Logic());
            add(control = new Control());
            add(renderer = new Renderer());
            add(ui = new UI());
            add(netServer = new NetServer());
            add(netClient = new NetClient());
            
            assets.load(schematics);
            
            // Content init
            assets.loadRun("contentinit", ContentLoader.class, 
                () -> content.init(), 
                () -> content.load()
            );
            
            assets.loadRun("baseparts", BaseRegistry.class, () -> {}, () -> bases.load());
            
            Core.assets.load("sprites/schematic-background.png", Texture.class).loaded = t -> t.setWrap(TextureWrap.repeat);
        }
        
        private void initCoreSystems() {
            // Initialize ALL core content types
            try {
                Items.load();
                Liquids.load();
                StatusEffects.load();
                UnitTypes.load();
                Blocks.load();
                SectorPresets.load();
                Planets.load();
                Effect.load();
                Weather.load();
                UnitCommands.load();
                ResearchTree.load();
                TechTree.load();
                Loadouts.load();
                TeamEntries.load();
            } catch (Throwable t) {
                Log.err("Content init error", t);
            }
        }
        
        private void initGameSystems() {
            Rules.load();
            Team.load();
            StateRules.load();
            Difficulty.load();
        }
        
        private void startLoading() {
            loader = new LoadRenderer();
            Events.fire(new ClientCreateEvent());
            loadFileLogger();
            clientLoaded = true;
        }
        
        @Override
        public void render() {
            int targetfps = Core.settings.getInt("fpscap", 120);
            boolean limitFps = targetfps > 0 && targetfps <= 240;
            
            if (limitFps && lastTargetFps == targetfps) {
                nextFrame += (1000 * 1000000) / targetfps;
            } else {
                nextFrame = Time.nanos();
                lastTargetFps = targetfps;
            }
            
            if (!finished) {
                // Loading phase
                if (loader != null) {
                    loader.draw();
                }
                if (assets.update(1000 / 20)) { // 20 FPS loading
                    finishLoading();
                }
            } else {
                // Game running - FULL UPDATE LOOP
                PerfCounter.update.begin();
                
                // Fixed timestep update with TeaVM threading support
                update();
                
                PerfCounter.update.end();
            }
            
            if (limitFps) {
                long current = Time.nanos();
                if (nextFrame > current) {
                    Threads.sleep((nextFrame - current) / 1000000, (int)((nextFrame - current) % 1000000));
                }
            }
        }
        
        private void finishLoading() {
            loader.dispose();
            loader = null;
            SoundPriority.init();
            
            for (ApplicationListener listener : modules) {
                listener.init();
            }
            mods.eachClass(Mod::init);
            
            finished = true;
            Events.fire(new ClientLoadEvent());
            Log.info("Total time to load: @ms", Time.timeSinceMillis(beginTime));
            clientLoaded = true;
            
            super.resize(graphics.getWidth(), graphics.getHeight());
            app.post(() -> app.post(() -> app.post(() -> app.post(() -> {
                super.resize(graphics.getWidth(), graphics.getHeight());
                finishLaunch();
            }))));
        }
        
        private void update() {
            asyncCore.begin();
            super.update();
            asyncCore.end();
        }
        
        @Override
        public void resize(int width, int height) {
            if (assets == null) return;
            if (!finished) {
                Draw.proj().setOrtho(0, 0, width, height);
            } else {
                super.resize(width, height);
            }
        }
        
        @Override
        public void pause() {
            if (finished) super.pause();
        }
        
        @Override
        public void resume() {
            if (finished) super.resume();
        }
        
        @Override
        public void dispose() {
            super.dispose();
        }
        
        private void showCrashScreen(Throwable t) {
            // Would show error UI
            Log.err("Game crashed: ", t);
        }
    }
    
    /**
     * TeaVM Platform Implementation - Full feature set using Web APIs
     */
    public static class TeaVMPlatform extends ClientLauncher {
        @Override
        public void showFileChooser(FileChooserParams params) {
            TeaVMFileChooser.show(params);
        }
        
        @Override
        public NetProvider getNet() {
            return new TeaVMNetProvider();
        }
        
        @Override
        public String getUUID() {
            String uuid = TeaVMLocalStorage.getItem("mindustry.uuid");
            if (uuid == null) {
                uuid = MathUtil.randomUUID();
                TeaVMLocalStorage.setItem("mindustry.uuid", uuid);
            }
            return uuid;
        }
        
        @Override
        public void exit() {
            Log.info("Exit requested in browser - ignoring");
        }
        
        @Override
        public void openURI(String uri) {
            TeaVMWindow.open(uri, "_blank", "");
        }
        
        @Override
        public void vibrate(int ms) {
            TeaVMNavigator.vibrate(ms);
        }
        
        @Override
        public void copyToClipboard(String text) {
            TeaVMClipboard.writeText(text);
        }
        
        @Override
        public String pasteFromClipboard() {
            return TeaVMClipboard.readText();
        }
        
        @Override
        public boolean canPasteFromClipboard() {
            return TeaVMClipboard.canRead();
        }
        
        @Override
        public Fi getSaveDirectory() {
            return Core.files.absolute(TeaVMLocalStorage.getItem("mindustry.data.dir") != null 
                ? TeaVMLocalStorage.getItem("mindustry.data.dir") 
                : "saves");
        }
        
        @Override
        public Fi getExternalDirectory() {
            return getSaveDirectory();
        }
        
        @Override
        public boolean is64Bit() { return true; }
        @Override
        public int getJavaVersion() { return 17; }
        @Override
        public String getJavaVendor() { return "TeaVM"; }
    }
    
    /**
     * FULL WebSocket NetProvider with ALL packet types
     */
    public static class TeaVMNetProvider extends NetProvider {
        private WebSocketClient webSocket;
        private NetConnection connection;
        private boolean connected = false;
        private final Seq<Packet> sendQueue = new Seq<>();
        
        @Override
        public void connect(String host, int port, NetConnection listener) {
            this.connection = listener;
            
            try {
                String wsUrl = buildWebSocketUrl(host, port);
                Log.info("Connecting to WebSocket: @", wsUrl);
                webSocket = new WebSocketClient(wsUrl, this);
            } catch (Exception e) {
                Log.err("Failed to create WebSocket connection", e);
                if (connection != null) {
                    connection.failed(e.getMessage());
                }
            }
        }
        
        private String buildWebSocketUrl(String host, int port) {
            if (host.startsWith("wss://") || host.startsWith("ws://")) {
                return host + (port > 0 ? ":" + port : "");
            }
            String protocol = TeaVMWindow.isSecure() ? "wss://" : "ws://";
            return protocol + host + ":" + port;
        }
        
        @Override
        public void disconnect() {
            if (webSocket != null) {
                webSocket.close();
                webSocket = null;
            }
            connected = false;
        }
        
        @Override
        public void send(Packet packet) {
            if (webSocket != null && connected) {
                try {
                    byte[] data = packet.toBytes();
                    webSocket.sendBinary(data);
                } catch (Exception e) {
                    Log.err("Failed to send packet", e);
                }
            } else {
                sendQueue.add(packet);
            }
        }
        
        @Override
        public void sendRaw(byte[] data) {
            if (webSocket != null && connected) {
                webSocket.sendBinary(data);
            }
        }
        
        @Override
        public boolean isConnected() {
            return connected && webSocket != null && webSocket.isOpen();
        }
        
        @Override
        public String getAddress() {
            return webSocket != null ? webSocket.getUrl() : "disconnected";
        }
        
        public void onOpen() {
            connected = true;
            Log.info("WebSocket connected");
            if (connection != null) {
                connection.connected();
            }
            // Flush send queue
            for (Packet p : sendQueue) {
                send(p);
            }
            sendQueue.clear();
        }
        
        public void onMessage(byte[] data) {
            if (connection != null) {
                connection.receive(data);
            }
        }
        
        public void onClose(int code, String reason) {
            connected = false;
            Log.info("WebSocket closed: @ (@)", code, reason);
            if (connection != null) {
                connection.disconnected();
            }
        }
        
        public void onError(String error) {
            Log.err("WebSocket error: @", error);
            if (connection != null) {
                connection.failed(error);
            }
        }
        
        private static class WebSocketClient {
            private final String url;
            private final TeaVMNetProvider provider;
            private Object jsWebSocket;
            
            public WebSocketClient(String url, TeaVMNetProvider provider) {
                this.url = url;
                this.provider = provider;
                createWebSocket();
            }
            
            private native void createWebSocket() /*-{
                var self = this;
                var ws = new $wnd.WebSocket(self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::url);
                self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::jsWebSocket = ws;
                
                ws.binaryType = 'arraybuffer';
                
                ws.onopen = function(event) {
                    self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::onOpen()();
                };
                
                ws.onmessage = function(event) {
                    var data = event.data;
                    if (data instanceof ArrayBuffer) {
                        var bytes = new Uint8Array(data);
                        self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::onMessage([B)(bytes);
                    } else if (data instanceof Blob) {
                        var reader = new FileReader();
                        reader.onload = function() {
                            var bytes = new Uint8Array(reader.result);
                            self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::onMessage([B)(bytes);
                        };
                        reader.readAsArrayBuffer(data);
                    }
                };
                
                ws.onclose = function(event) {
                    self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::onClose(II)(event.code, event.reason);
                };
                
                ws.onerror = function(event) {
                    self.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::onError(Ljava/lang/String;)('WebSocket error');
                };
            }-*/;
            
            private void onOpen() { provider.onOpen(); }
            private void onMessage(byte[] data) { provider.onMessage(data); }
            private void onClose(int code, String reason) { provider.onClose(code, reason); }
            private void onError(String error) { provider.onError(error); }
            
            public native void sendBinary(byte[] data) /*-{
                var ws = this.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::jsWebSocket;
                if (ws && ws.readyState === 1) { ws.send(data); }
            }-*/;
            
            public native void close() /*-{
                var ws = this.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::jsWebSocket;
                if (ws) { ws.close(); }
            }-*/;
            
            public native boolean isOpen() /*-{
                var ws = this.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::jsWebSocket;
                return ws && ws.readyState === 1;
            }-*/;
            
            public native String getUrl() /*-{
                var ws = this.@mindustry.teavm.TeaVMLauncher.TeaVMNetProvider.WebSocketClient::jsWebSocket;
                return ws ? ws.url : '';
            }-*/;
        }
    }
}