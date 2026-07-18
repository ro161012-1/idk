package java.util.concurrent;

public class CyclicBarrier {
    private final int parties;
    private int count;
    private final Runnable barrierCommand;
    private final Object lock = new Object();
    private boolean broken = false;
    
    public CyclicBarrier(int parties) { this(parties, null); }
    public CyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) throw new IllegalArgumentException();
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }
    
    public int await() throws InterruptedException, BrokenBarrierException {
        return await(0, java.util.concurrent.TimeUnit.NANOSECONDS);
    }
    
    public int await(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException, BrokenBarrierException {
        synchronized (lock) {
            if (broken) throw new BrokenBarrierException();
            int index = --count;
            if (index == 0) {
                boolean ranAction = false;
                try {
                    if (barrierCommand != null) barrierCommand.run();
                    ranAction = true;
                    nextGeneration();
                } finally {
                    if (!ranAction) breakBarrier();
                }
                return parties - 1;
            }
            try {
                lock.wait(unit.toMillis(timeout));
            } catch (InterruptedException e) {
                if (broken) throw new BrokenBarrierException();
                throw e;
            }
            if (broken) throw new BrokenBarrierException();
            return index;
        }
    }
    
    private void nextGeneration() {
        count = parties;
        lock.notifyAll();
    }
    
    private void breakBarrier() {
        broken = true;
        lock.notifyAll();
    }
    
    public int getParties() { return parties; }
    public int getNumberWaiting() { synchronized (lock) { return count; } }
    public boolean isBroken() { return broken; }
    public void reset() { synchronized (lock) { broken = false; count = parties; } }
}