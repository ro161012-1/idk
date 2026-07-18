package java.util;

/**
 * GWT emulation of AbstractCollection.
 */
public abstract class AbstractCollection<E> implements Collection<E> {
    protected AbstractCollection() {}
    
    public abstract Iterator<E> iterator();
    public abstract int size();
    
    public boolean isEmpty() { return size() == 0; }
    public boolean contains(Object o) {
        Iterator<E> it = iterator();
        if (o == null) { while (it.hasNext()) if (it.next() == null) return true; }
        else { while (it.hasNext()) if (o.equals(it.next())) return true; }
        return false;
    }
    public Object[] toArray() {
        Object[] result = new Object[size()];
        Iterator<E> it = iterator();
        for (int i = 0; i < result.length; i++) { if (!it.hasNext()) return Arrays.copyOf(result, i); result[i] = it.next(); }
        return result;
    }
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) a = Arrays.copyOf(a, size);
        Iterator<E> it = iterator();
        for (int i = 0; i < size; i++) { if (!it.hasNext()) return Arrays.copyOf(a, i); a[i] = it.next(); }
        if (a.length > size) a[size] = null;
        return a;
    }
    public boolean add(E e) { throw new UnsupportedOperationException(); }
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if (o == null) { while (it.hasNext()) if (it.next() == null) { it.remove(); return true; } }
        else { while (it.hasNext()) if (o.equals(it.next())) { it.remove(); return true; } }
        return false;
    }
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) if (!contains(e)) return false;
        return true;
    }
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) if (add(e)) modified = true;
        return modified;
    }
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) if (c.contains(it.next())) { it.remove(); modified = true; }
        return modified;
    }
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) if (!c.contains(it.next())) { it.remove(); modified = true; }
        return modified;
    }
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) { it.next(); it.remove(); }
    }
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            Object e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext()) break;
            sb.append(',').append(' ');
        }
        sb.append(']');
        return sb.toString();
    }
}
