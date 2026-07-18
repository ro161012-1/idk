package java.lang;

public class Thread implements Runnable {
    private Runnable target;
    private String name;
    private boolean daemon = false;
    private int priority = NORM_PRIORITY;
    private long id;
    private static long nextId = 1;
    private static Thread currentThread;
    
    public static final int MIN_PRIORITY = 1;
    public static final int NORM_PRIORITY = 5;
    public static final int MAX_PRIORITY = 10;
    
    public Thread() { this(null, "Thread-" + nextId++); }
    public Thread(Runnable target) { this(target, "Thread-" + nextId++); }
    public Thread(String name) { this(null, name); }
    public Thread(Runnable target, String name) { this.target = target; this.name = name; this.id = nextId++; }
    public Thread(ThreadGroup group, Runnable target) { this(target, "Thread-" + nextId++); }
    public Thread(ThreadGroup group, String name) { this(null, name); }
    public Thread(ThreadGroup group, Runnable target, String name) { this(target, name); }
    public Thread(ThreadGroup group, Runnable target, String name, long stackSize) { this(target, name); }
    
    public void run() { if (target != null) target.run(); }
    public void start() { if (currentThread == null) currentThread = this; run(); }
    public static void sleep(long millis) throws InterruptedException { /* GWT is single-threaded */ }
    public static void sleep(long millis, int nanos) throws InterruptedException { sleep(millis); }
    public static void yield() {}
    public static Thread currentThread() { if (currentThread == null) currentThread = new Thread(); return currentThread; }
    
    public final void setDaemon(boolean on) { daemon = on; }
    public final boolean isDaemon() { return daemon; }
    public final void setPriority(int newPriority) { priority = newPriority; }
    public final int getPriority() { return priority; }
    public final String getName() { return name; }
    public final void setName(String name) { this.name = name; }
    public final long getId() { return id; }
    public final boolean isAlive() { return false; }
    public void join() throws InterruptedException {}
    public void join(long millis) throws InterruptedException {}
    public void join(long millis, int nanos) throws InterruptedException {}
    public void interrupt() {}
    public static boolean interrupted() { return false; }
    public boolean isInterrupted() { return false; }
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {}
    public UncaughtExceptionHandler getUncaughtExceptionHandler() { return null; }
    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler eh) {}
    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() { return null; }
    
    public interface UncaughtExceptionHandler { void uncaughtException(Thread t, Throwable e); }
}
