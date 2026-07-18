package java.util.concurrent;

public class CountDownLatch {
    private final Object lock = new Object();
    private int count;
    
    public CountDownLatch(int count) { if (count < 0) throw new IllegalArgumentException(); this.count = count; }
    
    public void await() throws InterruptedException {
        synchronized (lock) { while (count > 0) lock.wait(); }
    }
    
    public boolean await(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        long deadline = System.nanoTime() + nanos;
        synchronized (lock) {
            while (count > 0) {
                if (nanos <= 0) return false;
                lock.wait(nanos / 1000000, (int)(nanos % 1000000));
                nanos = deadline - System.nanoTime();
            }
            return true;
        }
    }
    
    public void countDown() {
        synchronized (lock) {
            if (count > 0) {
                count--;
                if (count == 0) lock.notifyAll();
            }
        }
    }
    
    public long getCount() { return count; }
    public String toString() { return "CountDownLatch[count=" + count + "]"; }
}