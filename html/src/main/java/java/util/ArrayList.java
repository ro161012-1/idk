package java.util;

/**
 * GWT emulation of ArrayList - uses native JS array.
 */
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {
    
    private Object[] elementData;
    private int size = 0;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    public ArrayList() { this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA; }
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) this.elementData = new Object[initialCapacity];
        else if (initialCapacity == 0) this.elementData = EMPTY_ELEMENTDATA;
        else throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            if (elementData.getClass() != Object[].class) elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            elementData = EMPTY_ELEMENTDATA;
        }
    }
    
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        if (minCapacity - elementData.length > 0) grow(minCapacity);
    }
    
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0) newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }
    
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public boolean contains(Object o) { return indexOf(o) >= 0; }
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) if (elementData[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++) if (o.equals(elementData[i])) return i;
        }
        return -1;
    }
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) if (elementData[i] == null) return i;
        } else {
            for (int i = size - 1; i >= 0; i--) if (o.equals(elementData[i])) return i;
        }
        return -1;
    }
    
    public Object[] toArray() { return Arrays.copyOf(elementData, size); }
    public <T> T[] toArray(T[] a) {
        if (a.length < size) return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) a[size] = null;
        return a;
    }
    
    @SuppressWarnings("unchecked")
    E elementData(int index) { return (E) elementData[index]; }
    
    public E get(int index) { rangeCheck(index); return elementData(index); }
    
    public E set(int index, E element) { rangeCheck(index); E old = elementData(index); elementData[index] = element; return old; }
    
    public boolean add(E e) { ensureCapacityInternal(size + 1); elementData[size++] = e; return true; }
    public void add(int index, E element) { rangeCheckForAdd(index); ensureCapacityInternal(size + 1); System.arraycopy(elementData, index, elementData, index + 1, size - index); elementData[index] = element; size++; }
    
    public E remove(int index) { rangeCheck(index); E old = elementData(index); int numMoved = size - index - 1; if (numMoved > 0) System.arraycopy(elementData, index + 1, elementData, index, numMoved); elementData[--size] = null; return old; }
    public boolean remove(Object o) {
        if (o == null) { for (int i = 0; i < size; i++) if (elementData[i] == null) { fastRemove(i); return true; } }
        else { for (int i = 0; i < size; i++) if (o.equals(elementData[i])) { fastRemove(i); return true; } }
        return false;
    }
    
    private void fastRemove(int index) { int numMoved = size - index - 1; if (numMoved > 0) System.arraycopy(elementData, index + 1, elementData, index, numMoved); elementData[--size] = null; }
    
    public void clear() { for (int i = 0; i < size; i++) elementData[i] = null; size = 0; }
    
    public boolean addAll(Collection<? extends E> c) { Object[] a = c.toArray(); int numNew = a.length; ensureCapacityInternal(size + numNew); System.arraycopy(a, 0, elementData, size, numNew); size += numNew; return numNew != 0; }
    public boolean addAll(int index, Collection<? extends E> c) { rangeCheckForAdd(index); Object[] a = c.toArray(); int numNew = a.length; ensureCapacityInternal(size + numNew); int numMoved = size - index; if (numMoved > 0) System.arraycopy(elementData, index, elementData, index + numNew, numMoved); System.arraycopy(a, 0, elementData, index, numNew); size += numNew; return numNew != 0; }
    protected void removeRange(int fromIndex, int toIndex) { System.arraycopy(elementData, toIndex, elementData, fromIndex, size - toIndex); size -= toIndex - fromIndex; }
    
    private void rangeCheck(int index) { if (index >= size) throw new IndexOutOfBoundsException(outOfBoundsMsg(index)); }
    private void rangeCheckForAdd(int index) { if (index > size || index < 0) throw new IndexOutOfBoundsException(outOfBoundsMsg(index)); }
    private String outOfBoundsMsg(int index) { return "Index: " + index + ", Size: " + size; }
    
    public Object clone() { try { ArrayList<?> v = (ArrayList<?>) super.clone(); v.elementData = Arrays.copyOf(elementData, size); v.size = size; return v; } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
    public void trimToSize() { if (size < elementData.length) elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size); }
}