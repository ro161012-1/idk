package java.util.concurrent;

/**
 * GWT emulation of CompletableFuture - minimal implementation for Mindustry.
 * GWT doesn't support CompletableFuture, so we provide a stub.
 */
public class CompletableFuture<T> {
    
    private T result;
    private Throwable exception;
    private boolean done = false;
    private final Object lock = new Object();
    
    public static <U> CompletableFuture<U> completedFuture(U value) {
        CompletableFuture<U> f = new CompletableFuture<>();
        f.result = value;
        f.done = true;
        return f;
    }
    
    public static <U> CompletableFuture<U> failedFuture(Throwable ex) {
        CompletableFuture<U> f = new CompletableFuture<>();
        f.exception = ex;
        f.done = true;
        return f;
    }
    
    public static <U> CompletableFuture<U> supplyAsync(java.util.function.Supplier<U> supplier) {
        CompletableFuture<U> f = new CompletableFuture<>();
        try {
            f.result = supplier.get();
            f.done = true;
        } catch (Throwable t) {
            f.exception = t;
            f.done = true;
        }
        return f;
    }
    
    public boolean isDone() {
        return done;
    }
    
    public T get() throws InterruptedException, java.util.concurrent.ExecutionException {
        synchronized (lock) {
            while (!done) {
                lock.wait();
            }
        }
        if (exception != null) throw new java.util.concurrent.ExecutionException(exception);
        return result;
    }
    
    public T join() {
        try {
            return get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public <U> CompletableFuture<U> thenApply(java.util.function.Function<? super T, ? extends U> fn) {
        return thenApplyAsync(fn);
    }
    
    public <U> CompletableFuture<U> thenApplyAsync(java.util.function.Function<? super T, ? extends U> fn) {
        CompletableFuture<U> f = new CompletableFuture<>();
        try {
            if (exception != null) throw new RuntimeException(exception);
            f.result = fn.apply(result);
            f.done = true;
        } catch (Throwable t) {
            f.exception = t;
            f.done = true;
        }
        return f;
    }
    
    public CompletableFuture<Void> thenRun(Runnable action) {
        try {
            if (exception != null) throw new RuntimeException(exception);
            action.run();
        } catch (Throwable t) {
            // ignore
        }
        return CompletableFuture.completedFuture(null);
    }
    
    public void complete(T value) {
        synchronized (lock) {
            this.result = value;
            this.done = true;
            lock.notifyAll();
        }
    }
    
    public boolean completeExceptionally(Throwable ex) {
        synchronized (lock) {
            this.exception = ex;
            this.done = true;
            lock.notifyAll();
            return true;
        }
    }
}