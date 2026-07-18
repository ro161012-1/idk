# TeaVM Port Alternative

TeaVM compiles **Java bytecode → WebAssembly** directly, which is often more viable than GWT for large codebases.

## Why TeaVM over GWT?

| Feature | GWT | TeaVM |
|---------|-----|-------|
| Input | Java source | Java bytecode (.class) |
| Reflection | Very limited | Supported (with config) |
| Threading | No | WASM threads (experimental) |
| Existing libraries | Must port | Works if no natives |
| Build integration | Gradle plugin | Gradle/Maven plugin |
| Output size | Large JS | Compact WASM |
| Debugging | Source maps | Source maps |

## TeaVM Gradle Setup

```gradle
// html-teavm/build.gradle
plugins {
    id "java"
    id "org.teavm.gradle" version "0.10.0"  // Check latest
}

dependencies {
    implementation project(":core")
    implementation "org.teavm:teavm-classlib:0.10.0"
    implementation "org.teavm:teavm-jso:0.10.0"
    implementation "org.teavm:teavm-webgl:0.10.0"
    implementation "org.teavm:teavm-websocket:0.10.0"
    implementation "org.teavm:teavm-indexeddb:0.10.0"
}

teavm {
    targetDirectory = file("build/teavm")
    mainClass = "mindustry.html.TeaVMLauncher"
    
    // Enable reflection for specific packages
    reflection {
        include "mindustry.**"
        include "arc.**"
    }
    
    // Minification
    minify = true
    debugInformationGenerated = false
    
    // WASM target
    targetType = "wasm"
    wasm {
        optimizeForSize = true
        generateNamesSection = false
    }
    
    // Code splitting
    splitting = true
    splitThreshold = 100000
}
```

## TeaVM Launcher

```java
// TeaVMLauncher.java
package mindustry.html;

import org.teavm.jso.JSBody;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import arc.backends.teavm.*;
import mindustry.*;

public class TeaVMLauncher {
    public static void main(String[] args) {
        TeaVMApplicationConfiguration cfg = new TeaVMApplicationConfiguration();
        cfg.canvasId = "canvas";
        cfg.width = "100%";
        cfg.height = "100%";
        cfg.preferWebGL2 = true;
        
        new TeaVMApplication(new TeaVMClientLauncher(), cfg);
    }
}
```

## Reflection Configuration

Create `META-INF/teaVM/reflection.json`:

```json
{
  "reflection": {
    "packages": [
      "mindustry.content",
      "mindustry.entities",
      "mindustry.gen",
      "arc.graphics",
      "arc.assets"
    ],
    "classes": [
      "mindustry.Vars",
      "mindustry.content.Items",
      "mindustry.content.Blocks",
      "mindustry.content.UnitTypes"
    ]
  }
}
```

## Native Replacements Needed

| Native | TeaVM Replacement |
|--------|-------------------|
| `arc.backend.sdl.*` | `arc.backend.teavm.*` (create) |
| `java.io.File` | IndexedDB virtual FS |
| `java.net.Socket` | WebSocket API |
| `javax.sound.*` | Web Audio API |
| `java.nio.*` | TeaVM classlib |
| `java.util.concurrent` | TeaVM classlib (limited) |
| OpenGL | WebGL 2 via `org.teavm:teavm-webgl` |

## Building

```bash
./gradlew :html-teavm:teavmBuild
```

Output in `html-teavm/build/teavm/`

## Advantages for Mindustry

1. **Keeps generated code** (`mindustry.gen.*`) - no source rewriting
2. **Mods might work** - if they don't use natives
3. **Smaller output** - WASM is more compact than GWT JS
4. **Better performance** - WASM near-native speed

## Still Difficult

- Steam/Discord natives → stub implementations
- File dialogs → HTML input elements
- Multi-threading logic → rewrite or WASM threads
- Custom shaders → WebGL 2 conversion

## Recommendation

**Start with TeaVM** if you want to pursue a real port. It has a much higher chance of success for a codebase this size.