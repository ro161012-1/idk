package java.util.concurrent;

/**
 * GWT emulation of Executors - single-threaded execution in GWT.
 */
public class Executors {
    
    public static ExecutorService newSingleThreadExecutor() {
        return new SingleThreadExecutor();
    }
    
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new SingleThreadExecutor(); // GWT is single-threaded
    }
    
    public static ExecutorService newCachedThreadPool() {
        return new SingleThreadExecutor();
    }
    
    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return new SingleThreadScheduledExecutor();
    }
    
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new SingleThreadScheduledExecutor();
    }
    
    public static ThreadFactory defaultThreadFactory() {
        return r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        };
    }
    
    private static class SingleThreadExecutor implements ExecutorService {
        private final java.util.Queue<Runnable> queue = new java.util.LinkedList<>();
        private volatile boolean shutdown = false;
        
        @Override public void execute(Runnable command) {
            if (shutdown) throw new RejectedExecutionException();
            queue.add(command);
            // Run immediately in GWT (single-threaded)
            try { command.run(); } catch (Throwable t) { t.printStackTrace(); }
        }
        
        @Override public void shutdown() { shutdown = true; }
        @Override public java.util.List<Runnable> shutdownNow() { shutdown = true; return new java.util.ArrayList<>(queue); }
        @Override public boolean isShutdown() { return shutdown; }
        @Override public boolean isTerminated() { return shutdown && queue.isEmpty(); }
        @Override public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) { return true; }
        @Override public <T> java.util.concurrent.Future<T> submit(Callable<T> task) { return new FutureTask<>(task); }
        @Override public <T> java.util.concurrent.Future<T> submit(Runnable task, T result) { return new FutureTask<>(task, result); }
        @Override public java.util.concurrent.Future<?> submit(Runnable task) { return new FutureTask<>(task, null); }
        @Override public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(java.util.Collection<? extends Callable<T>> tasks) { return new java.util.ArrayList<>(); }
        @Override public <T> T invokeAny(java.util.Collection<? extends Callable<T>> tasks) { return null; }
    }
    
    private static class SingleThreadScheduledExecutor implements ScheduledExecutorService {
        @Override public void execute(Runnable command) { command.run(); }
        @Override public void shutdown() {}
        @Override public java.util.List<Runnable> shutdownNow() { return new java.util.ArrayList<>(); }
        @Override public boolean isShutdown() { return false; }
        @Override public boolean isTerminated() { return false; }
        @Override public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) { return true; }
        @Override public <T> java.util.concurrent.Future<T> submit(Callable<T> task) { return new FutureTask<>(task); }
        @Override public <T> java.util.concurrent.Future<T> submit(Runnable task, T result) { return new FutureTask<>(task, result); }
        @Override public java.util.concurrent.Future<?> submit(Runnable task) { return new FutureTask<>(task, null); }
        @Override public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(java.util.Collection<? extends Callable<T>> tasks) { return new java.util.ArrayList<>(); }
        @Override public <T> T invokeAny(java.util.Collection<? extends Callable<T>> tasks) { return null; }
        @Override public ScheduledFuture<?> schedule(Runnable command, long delay, java.util.concurrent.TimeUnit unit) { return null; }
        @Override public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, java.util.concurrent.TimeUnit unit) { return null; }
        @Override public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, java.util.concurrent.TimeUnit unit) { return null; }
        @Override public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, java.util.concurrent.TimeUnit unit) { return null; }
    }
}