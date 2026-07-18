package java.lang;

public class Runtime {
    private static final Runtime currentRuntime = new Runtime();
    
    public static Runtime getRuntime() { return currentRuntime; }
    
    public void exit(int status) { System.exit(status); }
    public void addShutdownHook(Thread hook) {}
    public boolean removeShutdownHook(Thread hook) { return false; }
    public void halt(int status) { System.exit(status); }
    public Process exec(String command) throws java.io.IOException { return null; }
    public Process exec(String command, String[] envp) throws java.io.IOException { return null; }
    public Process exec(String command, String[] envp, java.io.File dir) throws java.io.IOException { return null; }
    public Process exec(String[] cmdarray) throws java.io.IOException { return null; }
    public Process exec(String[] cmdarray, String[] envp) throws java.io.IOException { return null; }
    public Process exec(String[] cmdarray, String[] envp, java.io.File dir) throws java.io.IOException { return null; }
    public int availableProcessors() { return 1; }
    public long freeMemory() { return Runtime.getRuntime().maxMemory(); }
    public long totalMemory() { return Runtime.getRuntime().maxMemory(); }
    public long maxMemory() { return 1024 * 1024 * 1024; }
    public void gc() { System.gc(); }
    public void runFinalization() { System.runFinalization(); }
    public void traceInstructions(boolean on) {}
    public void traceMethodCalls(boolean on) {}
    public void load(String filename) { throw new UnsupportedOperationException(); }
    public void loadLibrary(String libname) { throw new UnsupportedOperationException(); }
}