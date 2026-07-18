package java.util.concurrent.atomic;

/**
 * GWT emulation of AtomicInteger - non-atomic in GWT (single-threaded).
 */
public class AtomicInteger extends Number implements java.io.Serializable {
    private int value;
    
    public AtomicInteger() { this(0); }
    public AtomicInteger(int initialValue) { value = initialValue; }
    
    public final int get() { return value; }
    public final void set(int newValue) { value = newValue; }
    public final int getAndSet(int newValue) { int old = value; value = newValue; return old; }
    public final boolean compareAndSet(int expect, int update) {
        if (value == expect) { value = update; return true; }
        return false;
    }
    public final int getAndIncrement() { return value++; }
    public final int getAndDecrement() { return value--; }
    public final int getAndAdd(int delta) { int old = value; value += delta; return old; }
    public final int incrementAndGet() { return ++value; }
    public final int decrementAndGet() { return --value; }
    public final int addAndGet(int delta) { value += delta; return value; }
    
    @Override public int intValue() { return value; }
    @Override public long longValue() { return value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }
    @Override public String toString() { return Integer.toString(value); }
}