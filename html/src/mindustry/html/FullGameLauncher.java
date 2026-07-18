package mindustry.html;

import arc.backends.gwt.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
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
 * Full game launcher attempting to initialize maximum Mindustry systems in GWT.
 * This creates stub implementations for excluded classes and HTML5-native implementations
 * for platform-specific features.
 */
public class FullGameLauncher extends GwtApplication {
    
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration();
        
        cfg.width = "100%";
        cfg.height = "100%";
        cfg.padHorizontal = 0;
        cfg.padVertical = 0;
        
        // WebGL 2 with anti-aliasing
        cfg.antialiasing = true;
        cfg.stencil = true;
        cfg.alpha = false;
        cfg.premultipliedAlpha = true;
        cfg.preserveDrawingBuffer = false;
        cfg.preferWebGL2 = true;
        cfg.failIfMajorPerformanceCaveat = false;
        
        // Audio via Web Audio API
        cfg.useAudio = true;
        
        // Prevent default browser behavior for game keys
        cfg.preventDefaultKeys = new int[]{
            32, 37, 38, 39, 40, 13, 27, 9, 16, 17, 18,  // space, arrows, enter, esc, tab, shift, ctrl, alt
            65, 83, 68, 87, 69, 81, 82, 70,               // WASD, E, Q, R, F
            49, 50, 51, 52, 53, 54, 55, 56, 57, 48        // 0-9
        };
        
        return cfg;
    }
    
    @Override
    public ApplicationListener createApplicationListener() {
        // Initialize platform BEFORE any Mindustry static initialization
        HtmlPlatform platform = new HtmlPlatform();
        Platform.platform = platform;
        
        // Initialize minimal logging first
        initLogging();
        
        return new FullGame(platform);
    }
    
    private void initLogging() {
        // GWT-compatible logging setup
        Log.info("Mindustry HTML5 - Initializing...");
    }
    
    /**
     * Main game class - mimics ClientLauncher but GWT-compatible
     */
    public static class FullGame implements ApplicationListener {
        private final HtmlPlatform platform;
        private LoadRenderer loader;
        private long beginTime;
        private boolean finished = false;
        private long nextFrame;
        private long lastTargetFps = -1;
        
        public FullGame(HtmlPlatform platform) {
            this.platform = platform;
        }
        
        @Override
        public void create() {
            beginTime = Time.millis();
            
            // Initialize core systems in order
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
            // Set data directory to IndexedDB
            String dataDir = GwtLocalStorage.getItem("mindustry.data.dir");
            if (dataDir != null) {
                Core.settings.setDataDirectory(files.absolute(dataDir));
            }
            
            platform = this.platform;
            maxTextureSize = Gl.getInt(Gl.maxTextureSize);
            
            Log.info("[GL] Version: @", graphics.getGLVersion());
            Log.info("[GL] Max texture size: @", maxTextureSize);
            Log.info("[GL] Using @ API.", gl30 != null ? "OpenGL 3" : "OpenGL 2");
            
            // Time delta provider for 60fps fixed timestep
            Time.setDeltaProvider(() -> {
                float result = Core.graphics.getDeltaTime() * 60f;
                return (Float.isNaN(result) || Float.isInfinite(result)) ? 1f : Mathf.clamp(result, 0.0001f, 10f);
            });
        }
        
        private void initGraphics() {
            batch = new SpriteBatch();
            assets = new AssetManager();
            assets.setLoader(Texture.class, "." + mapExtension, new MapPreviewLoader());
            
            tree = new FileTree();
            
            // Sound/Music loaders using Web Audio
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
                    // Load async on main thread
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
            
            // Network
            Vars.net = new Net(platform.getNet());
            MapPreviewLoader.setupLoaders();
            
            // Mods (stubbed - no actual mod loading in HTML5)
            mods = new HtmlMods();
            schematics = new Schematics();
            
            Fonts.loadSystemCursors();
            
            // Load Vars (this initializes all content)
            assets.load(new HtmlVars());
            
            Fonts.loadDefaultFont();
            
            // Atlas - use fallback if texture size too small
            String atlasPath = maxTextureSize >= 4096 ? "sprites/sprites.aatls" : "sprites/fallback/sprites.aatls";
            assets.load(new AssetDescriptor<>(atlasPath, TextureAtlas.class)).loaded = t -> atlas = t;
            
            assets.loadRun("maps", Map.class, () -> maps.loadPreviews());
            
            Musics.load();
            Sounds.load();
            
            // Content creation - NO mods, NO scripts
            assets.loadRun("contentcreate", Content.class, 
                () -> content.createBaseContent(),
                () -> content.loadColors()
            );
            
            assets.load(mods);
            assets.loadRun("mergeUI", PixmapPacker.class, () -> {}, () -> Fonts.mergeFontAtlas(atlas));
            
            // Core modules
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
            // Initialize core content
            Items.load();
            Liquids.load();
            StatusEffects.load();
            UnitTypes.load();
            Blocks.load();
            SectorPresets.load();
            Planets.load();
        }
        
        private void initGameSystems() {
            // Game rules, teams, etc.
            Rules.load();
            Team.load();
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
                // Game running
                PerfCounter.update.begin();
                
                // Fixed timestep update
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
        }
    }
    
    /**
     * HTML5 Platform Implementation
     */
    public static class HtmlPlatform extends ClientLauncher {
        @Override
        public void showFileChooser(FileChooserParams params) {
            GwtFileChooser.show(params);
        }
        
        @Override
        public NetProvider getNet() {
            return new HtmlNetProvider();
        }
        
        @Override
        public String getUUID() {
            String uuid = GwtLocalStorage.getItem("mindustry.uuid");
            if (uuid == null) {
                uuid = MathUtil.randomUUID();
                GwtLocalStorage.setItem("mindustry.uuid", uuid);
            }
            return uuid;
        }
        
        @Override
        public void exit() {
            Log.info("Exit requested in browser - ignoring");
        }
        
        @Override
        public void openURI(String uri) {
            GwtWindow.open(uri, "_blank", "");
        }
        
        @Override
        public void vibrate(int ms) {
            GwtNavigator.vibrate(ms);
        }
        
        @Override
        public void copyToClipboard(String text) {
            GwtClipboard.writeText(text);
        }
        
        @Override
        public String pasteFromClipboard() {
            return GwtClipboard.readText();
        }
        
        @Override
        public boolean canPasteFromClipboard() {
            return GwtClipboard.canRead();
        }
    }
    
    /**
     * Stub Mods class - no actual mod loading in HTML5
     */
    public static class HtmlMods extends Mods {
        @Override
        public void load() {
            // Don't load external mods
            Log.info("HTML5: Mod loading disabled");
        }
        
        @Override
        public void loadScripts() {
            // No script loading
        }
        
        @Override
        public Seq<Mod> getMods() {
            return new Seq<>();
        }
    }
    
    /**
     * Stub Vars - minimal initialization
     */
    public static class HtmlVars extends Vars {
        @Override
        public void load(AssetManager assets) {
            // Minimal vars initialization
            super.load(assets);
        }
    }
}