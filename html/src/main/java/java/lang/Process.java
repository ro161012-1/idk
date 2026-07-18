package java.lang;

public abstract class Process {
    public abstract OutputStream getOutputStream();
    public abstract InputStream getInputStream();
    public abstract InputStream getErrorStream();
    public abstract int waitFor() throws InterruptedException;
    public abstract int exitValue();
    public abstract void destroy();
    public boolean waitFor(long timeout, java.util.concurrent.TimeUnit unit) { return false; }
    public Process destroyForcibly() { destroy(); return this; }
    public boolean isAlive() { return false; }
    public long pid() { return -1; }
}