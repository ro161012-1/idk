package java.lang;

public final class System {
    public static native void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) /*-{
        var srcArr = src;
        var destArr = dest;
        for (var i = 0; i < length; i++) {
            destArr[destPos + i] = srcArr[srcPos + i];
        }
    }-*/;
    
    public static long currentTimeMillis() { return Date.now(); }
    public static native long nanoTime() /*-{
        return $wnd.performance.now() * 1000000;
    }-*/;
    
    public static void exit(int status) { throw new RuntimeException("System.exit(" + status + ")"); }
    public static String getProperty(String key) {
        if ("java.version".equals(key)) return "1.8.0_gwt";
        if ("java.vendor".equals(key)) return "GWT";
        if ("os.name".equals(key)) return "Browser";
        if ("os.arch".equals(key)) return "wasm";
        if ("user.dir".equals(key)) return "/";
        if ("file.separator".equals(key)) return "/";
        if ("path.separator".equals(key)) return ":";
        if ("line.separator".equals(key)) return "\n";
        return null;
    }
    public static String getProperty(String key, String def) { String v = getProperty(key); return v != null ? v : def; }
    public static String getenv(String name) { return null; }
    public static String getenv() { return ""; }
    public static void gc() {}
    public static void runFinalization() {}
    public static void load(String filename) { throw new UnsupportedOperationException("System.load"); }
    public static void loadLibrary(String libname) { throw new UnsupportedOperationException("System.loadLibrary"); }
    public static String mapLibraryName(String libname) { return libname; }
    
    public static final PrintStream out = new PrintStream();
    public static final PrintStream err = new PrintStream();
    public static final InputStream in = new InputStream() { public int read() { return -1; } };
    
    public static void setOut(PrintStream out) {}
    public static void setErr(PrintStream err) {}
    public static void setIn(InputStream in) {}
    
    public static Console console() { return null; }
    
    public static int identityHashCode(Object x) { return x.hashCode(); }
}
