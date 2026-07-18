package java.util.concurrent;

public class Semaphore {
    private final Object lock = new Object();
    private int permits;
    private final boolean fair;
    
    public Semaphore(int permits) { this(permits, false); }
    public Semaphore(int permits, boolean fair) { if (permits < 0) throw new IllegalArgumentException(); this.permits = permits; this.fair = fair; }
    
    public void acquire() throws InterruptedException { synchronized (lock) { while (permits <= 0) lock.wait(); permits--; } }
    public void acquire(int permits) throws InterruptedException { if (permits < 0) throw new IllegalArgumentException(); synchronized (lock) { while (this.permits < permits) lock.wait(); this.permits -= permits; } }
    public void release() { synchronized (lock) { permits++; lock.notify(); } }
    public void release(int permits) { if (permits < 0) throw new IllegalArgumentException(); synchronized (lock) { this.permits += permits; lock.notifyAll(); } }
    public boolean tryAcquire() { synchronized (lock) { if (permits > 0) { permits--; return true; } return false; } }
    public boolean tryAcquire(int permits) { if (permits < 0) throw new IllegalArgumentException(); synchronized (lock) { if (this.permits >= permits) { this.permits -= permits; return true; } return false; } }
    public boolean tryAcquire(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException { long nanos = unit.toNanos(timeout); long deadline = System.nanoTime() + nanos; synchronized (lock) { while (permits <= 0) { if (nanos <= 0) return false; lock.wait(nanos / 1000000, (int)(nanos % 1000000)); nanos = deadline - System.nanoTime(); } permits--; return true; } }
    public int availablePermits() { synchronized (lock) { return permits; } }
    public int getQueueLength() { return 0; }
    public boolean hasQueuedThreads() { return false; }
    public void drainPermits() { synchronized (lock) { permits = 0; } }
    public boolean isFair() { return fair; }
}