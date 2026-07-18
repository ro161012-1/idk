package java.lang;

public class PrintStream extends java.io.OutputStream {
    public PrintStream() {}
    
    public void print(boolean b) { consoleLog(b ? "true" : "false"); }
    public void print(char c) { consoleLog(String.valueOf(c)); }
    public void print(int i) { consoleLog(Integer.toString(i)); }
    public void print(long l) { consoleLog(Long.toString(l)); }
    public void print(float f) { consoleLog(Float.toString(f)); }
    public void print(double d) { consoleLog(Double.toString(d)); }
    public void print(char[] s) { consoleLog(new String(s)); }
    public void print(String s) { consoleLog(s == null ? "null" : s); }
    public void print(Object obj) { consoleLog(String.valueOf(obj)); }
    
    public void println() { consoleLog(""); }
    public void println(boolean b) { print(b); println(); }
    public void println(char c) { print(c); println(); }
    public void println(int i) { print(i); println(); }
    public void println(long l) { print(l); println(); }
    public void println(float f) { print(f); println(); }
    public void println(double d) { print(d); println(); }
    public void println(char[] s) { print(s); println(); }
    public void println(String s) { print(s); println(); }
    public void println(Object obj) { print(obj); println(); }
    
    public PrintStream printf(String format, Object... args) { print(String.format(format, args)); return this; }
    public PrintStream format(String format, Object... args) { return printf(format, args); }
    
    @Override public void write(int b) { print((char)b); }
    @Override public void write(byte[] b) { print(new String(b)); }
    @Override public void write(byte[] b, int off, int len) { print(new String(b, off, len)); }
    @Override public void flush() {}
    @Override public void close() {}
    
    private native void consoleLog(String s) /*-{
        console.log(s);
    }-*/;
}
