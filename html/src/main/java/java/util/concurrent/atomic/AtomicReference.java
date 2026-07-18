package java.util.concurrent.atomic;

/**
 * GWT emulation of AtomicReference - non-atomic in GWT (single-threaded).
 */
public class AtomicReference<V> implements java.io.Serializable {
    private V value;
    
    public AtomicReference() { this(null); }
    public AtomicReference(V initialValue) { value = initialValue; }
    
    public final V get() { return value; }
    public final void set(V newValue) { value = newValue; }
    public final V getAndSet(V newValue) { V old = value; value = newValue; return old; }
    public final boolean compareAndSet(V expect, V update) {
        if (value == expect || (value != null && value.equals(expect))) {
            value = update;
            return true;
        }
        return false;
    }
    
    @Override public String toString() { return String.valueOf(value); }
}