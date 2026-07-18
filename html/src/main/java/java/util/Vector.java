package java.util;

public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {
    protected Object[] elementData;
    protected int elementCount;
    protected int capacityIncrement;
    
    public Vector() { this(10); }
    public Vector(int initialCapacity) { this(initialCapacity, 0); }
    public Vector(int initialCapacity, int capacityIncrement) {
        if (initialCapacity < 0) throw new IllegalArgumentException();
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }
    public Vector(Collection<? extends E> c) { elementData = c.toArray(); elementCount = elementData.length; }
    
    @Override public synchronized int size() { return elementCount; }
    @Override public synchronized boolean isEmpty() { return elementCount == 0; }
    @Override public synchronized boolean contains(Object o) { return indexOf(o) >= 0; }
    @Override public synchronized int indexOf(Object o) { return indexOf(o, 0); }
    public synchronized int indexOf(Object o, int index) {
        if (o == null) { for (int i = index; i < elementCount; i++) if (elementData[i] == null) return i; }
        else { for (int i = index; i < elementCount; i++) if (o.equals(elementData[i])) return i; }
        return -1;
    }
    @Override public synchronized int lastIndexOf(Object o) { return lastIndexOf(o, elementCount - 1); }
    public synchronized int lastIndexOf(Object o, int index) {
        if (index >= elementCount) throw new IndexOutOfBoundsException();
        if (o == null) { for (int i = index; i >= 0; i--) if (elementData[i] == null) return i; }
        else { for (int i = index; i >= 0; i--) if (o.equals(elementData[i])) return i; }
        return -1;
    }
    @Override public synchronized E get(int index) { if (index >= elementCount) throw new ArrayIndexOutOfBoundsException(); return (E) elementData[index]; }
    @Override public synchronized E set(int index, E element) { if (index >= elementCount) throw new ArrayIndexOutOfBoundsException(); E old = (E) elementData[index]; elementData[index] = element; return old; }
    @Override public synchronized boolean add(E e) { ensureCapacity(elementCount + 1); elementData[elementCount++] = e; return true; }
    @Override public synchronized void add(int index, E element) { ensureCapacity(elementCount + 1); System.arraycopy(elementData, index, elementData, index + 1, elementCount - index); elementData[index] = element; elementCount++; }
    @Override public synchronized E remove(int index) { if (index >= elementCount) throw new ArrayIndexOutOfBoundsException(); E old = (E) elementData[index]; System.arraycopy(elementData, index + 1, elementData, index, elementCount - index - 1); elementData[--elementCount] = null; return old; }
    public synchronized boolean remove(Object o) { int i = indexOf(o); if (i >= 0) { remove(i); return true; } return false; }
    @Override public synchronized void clear() { for (int i = 0; i < elementCount; i++) elementData[i] = null; elementCount = 0; }
    private void ensureCapacity(int minCapacity) { if (minCapacity > elementData.length) grow(minCapacity); }
    private void grow(int minCapacity) { int oldCapacity = elementData.length; int newCapacity = oldCapacity + (capacityIncrement > 0 ? capacityIncrement : oldCapacity); if (newCapacity < minCapacity) newCapacity = minCapacity; elementData = Arrays.copyOf(elementData, newCapacity); }
}
