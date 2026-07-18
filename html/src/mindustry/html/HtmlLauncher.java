package mindustry.html;

import arc.backends.gwt.*;
import arc.files.*;
import arc.graphics.*;
import arc.util.*;
import mindustry.*;
import mindustry.desktop.*;

/**
 * Minimal HTML5 launcher for Mindustry.
 * This avoids pulling in the full ClientLauncher which has too many
 * GWT-incompatible dependencies.
 */
public class HtmlLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration();
        
        // Canvas size - use full window
        cfg.width = "100%";
        cfg.height = "100%";
        cfg.padHorizontal = 0;
        cfg.padVertical = 0;
        
        // WebGL settings
        cfg.antialiasing = true;
        cfg.stencil = true;
        cfg.alpha = false;
        cfg.premultipliedAlpha = true;
        cfg.preserveDrawingBuffer = false;
        cfg.preferWebGL2 = true;
        cfg.failIfMajorPerformanceCaveat = false;
        
        // Audio
        cfg.useAudio = true;
        
        // Input - prevent default for game keys
        cfg.preventDefaultKeys = new int[]{
            32, 37, 38, 39, 40, 13, 27, 9, 16, 17, 18
        };
        
        return cfg;
    }
    
    @Override
    public ApplicationListener createApplicationListener() {
        // Initialize minimal logging
        Vars.loadLogger();
        
        // Return a minimal game instance
        return new MinimalGame();
    }
    
    /**
     * Minimal game implementation for HTML5 demo.
     * Does NOT extend ClientLauncher - avoids all the incompatible code.
     */
    public static class MinimalGame implements ApplicationListener {
        private boolean initialized = false;
        
        @Override
        public void create() {
            if (initialized) return;
            initialized = true;
            
            Gdx.app.log("MindustryHTML", "Initializing minimal game...");
            
            // Initialize core systems that work in GWT
            try {
                // Basic graphics setup
                UI.loadColors();
                
                // Load essential assets only
                Core.assets.load("sprites/error.png", Texture.class);
                
                Gdx.app.log("MindustryHTML", "Minimal game created successfully");
            } catch (Throwable t) {
                Gdx.app.error("MindustryHTML", "Failed to initialize", t);
            }
        }
        
        @Override
        public void resize(int width, int height) {
            // Handle resize
        }
        
        @Override
        public void render() {
            // Main render loop
            if (!initialized) return;
            
            // Clear screen
            Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            // TODO: Add actual game rendering here
        }
        
        @Override
        public void pause() {}
        
        @Override
        public void resume() {}
        
        @Override
        public void dispose() {}
    }
}