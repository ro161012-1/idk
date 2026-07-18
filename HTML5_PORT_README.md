# Mindustry HTML5 Port

This is a **GWT (Google Web Toolkit)** port of Mindustry that compiles the Java codebase to JavaScript/WebAssembly for browser play.

## ⚠️ Current Status: Experimental

This port is **incomplete** and **will not fully compile** without significant additional work. The Mindustry codebase heavily uses:
- Java reflection (mod loading, serialization, entity generation)
- Multi-threading (async logic, asset loading, networking)
- Native libraries (Steam, Discord RPC, SDL, file dialogs)
- Complex OpenGL shaders
- File I/O APIs

GWT only supports a subset of Java and requires all code to be GWT-compatible.

## 📁 Structure

```
html/
├── build.gradle              # GWT build configuration
├── src/
│   └── mindustry/html/
│       ├── MindustryGwt.gwt.xml      # GWT module descriptor
│       ├── HtmlLauncher.java         # Main entry point
│       ├── HtmlNetProvider.java      # WebSocket networking
│       └── js/                       # JSNI bindings
│           ├── GwtFileChooser.java
│           ├── GwtLocalStorage.java
│           ├── GwtWindow.java
│           ├── GwtNavigator.java
│           └── GwtClipboard.java
│       └── web/
│           └── index.html            # HTML shell
```

## 🛠 Requirements

- **Java 17+** (JDK, not just JRE)
- **Gradle** (included via `./gradlew`)
- **Memory**: 8GB+ RAM recommended for GWT compilation

## 🚀 Building

### Development Mode (SuperDevMode)
```bash
./gradlew :html:superDev
```
Then open http://localhost:9876 in browser, click "Dev Mode On", then open http://localhost:8888

### Production Build
```bash
./gradlew :html:compileGwt
```
Output in `html/build/war/mindustry/`

### WAR Deployment
```bash
./gradlew :html:war
```
Creates `html/build/libs/mindustry.war` for servlet containers (Tomcat, Jetty)

## 🔧 Major Challenges to Complete

### 1. GWT-Incompatible Code (Critical)
The following Mindustry systems **must be rewritten or excluded**:

| System | Issue | Solution |
|--------|-------|----------|
| `mindustry.gen.*` | Generated code uses reflection | Exclude from GWT; use manual implementations |
| `mindustry.mod.*` | Class loading, reflection | Disable mods in HTML5 build |
| `mindustry.ai.*` | Threading, complex logic | Simplify or port to single-threaded |
| `mindustry.net.*` | TCP sockets, serialization | WebSocket-only (partially done) |
| `mindustry.graphics.Shaders` | GLSL → WebGL translation | Convert shaders to WebGL 2 compatible |
| `arc.struct.Seq/ObjectMap` | Custom collections | Use GWT-compatible alternatives |
| `arc.util.Log` | JUL logging | Use GWT logging emulation |
| `arc.files.Fi` | File I/O | IndexedDB/LocalStorage backend |

### 2. Missing GWT Emulation
Mindustry uses many JDK classes not in GWT's JRE emulation:
- `java.nio.*` (buffers, channels)
- `java.util.concurrent.*` (threading)
- `java.lang.reflect.*` (reflection)
- `javax.imageio.*` (image loading)
- `java.security.*` (crypto)

### 3. Asset Pipeline
- Sprite atlas packing (`.aatls`) needs WebGL-compatible format
- Music (OGG) → Web Audio API
- Sounds (OGG/WAV) → Web Audio API
- Maps, schematics → JSON/ArrayBuffer

### 4. Performance
- GWT produces large JS bundles (~50-100MB)
- Need code splitting / lazy loading
- WebGL 2 required for compute shaders

## 🎯 Recommended Approach

### Option A: Minimal Playable Demo (Weeks)
1. Strip to single-player survival only
2. Remove: mods, multiplayer, steam, discord, editor, AI
3. Hardcode core content (no dynamic loading)
4. Use TeaVM instead of GWT for better WASM support

### Option B: CheerpJ (Days, but heavy)
```bash
# Run existing JVM bytecode in browser via WebAssembly
# Large download (~50MB), slower, but runs unmodified code
```

### Option C: TeaVM (Weeks)
```bash
# Compiles Java bytecode → WebAssembly
# Better performance than CheerpJ, supports threading via WASM threads
# Still needs native replacements
```

### Option D: Official Web Export (Exists!)
Mindustry already has **official web builds** on:
- **itch.io**: https://anuke.itch.io/mindustry (browser playable)
- **Steam**: "Mindustry" has web export

## 📝 Next Steps for Full Port

If you want to continue this GWT port:

1. **Create GWT emulation library** for missing JDK classes
   ```
   html/src/main/java/java/util/concurrent/...
   html/src/main/java/java/nio/...
   ```

2. **Exclude incompatible modules** in `MindustryGwt.gwt.xml`
   ```xml
   <exclude name="**/gen/**"/>
   <exclude name="**/mod/**"/>
   <exclude name="**/ai/**"/>
   <exclude name="**/steam/**"/>
   <exclude name="**/discord/**"/>
   ```

3. **Rewrite asset loading** for web (IndexedDB, fetch API)

4. **Convert shaders** from GLSL 330 to WebGL 2 (ESSL 300)

5. **Implement WebSocket server** for multiplayer (modify server module)

6. **Test incrementally** - compile one package at a time

## 🔗 Resources

- [GWT Documentation](http://www.gwtproject.org/doc/latest/)
- [libGDX GWT Backend](https://libgdx.com/wiki/start/project-setup-gwt)
- [Arc Framework](https://github.com/Anuken/Arc) (libGDX fork used by Mindustry)
- [TeaVM](https://github.com/konsoletyper/teavm) - Alternative Java→WASM
- [CheerpJ](https://leaningtech.com/cheerpj/) - JVM in browser

## 💡 Quick Test

If you have Java 17+, try:
```bash
cd /home/user/idk
./gradlew :html:compileGwt --info 2>&1 | head -100
```

You'll see compilation errors for each incompatible class - that's your TODO list.

---

**Recommendation**: For playing in browser, use the **official itch.io web build**. For developing a custom web version, consider **TeaVM** or a **TypeScript/JavaScript rewrite** of core gameplay only.