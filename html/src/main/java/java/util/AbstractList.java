package java.util;

/**
 * GWT emulation of AbstractList.
 */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    protected AbstractList() {}
    
    public boolean add(E e) { add(size(), e); return true; }
    public E get(int index) { throw new IndexOutOfBoundsException("Index: " + index); }
    public E set(int index, E element) { throw new IndexOutOfBoundsException("Index: " + index); }
    public void add(int index, E element) { throw new UnsupportedOperationException(); }
    public E remove(int index) { throw new UnsupportedOperationException(); }
    public int indexOf(Object o) {
        ListIterator<E> it = listIterator();
        if (o == null) { while (it.hasNext()) if (it.next() == null) return it.previousIndex(); }
        else { while (it.hasNext()) if (o.equals(it.next())) return it.previousIndex(); }
        return -1;
    }
    public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        if (o == null) { while (it.hasPrevious()) if (it.previous() == null) return it.nextIndex(); }
        else { while (it.hasPrevious()) if (o.equals(it.previous())) return it.nextIndex(); }
        return -1;
    }
    public void clear() { removeRange(0, size()); }
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (E e : c) { add(index++, e); modified = true; }
        return modified;
    }
    public Iterator<E> iterator() { return new Itr(); }
    public ListIterator<E> listIterator() { return listIterator(0); }
    public ListIterator<E> listIterator(final int index) {
        rangeCheckForAdd(index);
        return new ListItr(index);
    }
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList(this, 0, fromIndex, toIndex);
    }
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> it = listIterator(fromIndex);
        for (int i = 0, n = toIndex - fromIndex; i < n; i++) { it.next(); it.remove(); }
    }
    private void rangeCheckForAdd(int index) { if (index < 0 || index > size()) throw new IndexOutOfBoundsException("Index: " + index); }
    
    private class Itr implements Iterator<E> {
        int cursor = 0, lastRet = -1, expectedModCount = 0;
        public boolean hasNext() { return cursor != size(); }
        public E next() { checkForComodification(); int i = cursor; if (i >= size()) throw new NoSuchElementException(); cursor = i + 1; return get(lastRet = i); }
        public void remove() { if (lastRet < 0) throw new IllegalStateException(); checkForComodification(); try { AbstractList.this.remove(lastRet); cursor = lastRet; lastRet = -1; expectedModCount = 0; } catch (IndexOutOfBoundsException ex) { throw new ConcurrentModificationException(); } }
        void checkForComodification() { if (expectedModCount != 0) throw new ConcurrentModificationException(); }
    }
    
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) { cursor = index; }
        public boolean hasPrevious() { return cursor != 0; }
        public int nextIndex() { return cursor; }
        public int previousIndex() { return cursor - 1; }
        public E previous() { checkForComodification(); int i = cursor - 1; if (i < 0) throw new NoSuchElementException(); cursor = i; return get(lastRet = i); }
        public void set(E e) { if (lastRet < 0) throw new IllegalStateException(); checkForComodification(); try { AbstractList.this.set(lastRet, e); } catch (IndexOutOfBoundsException ex) { throw new ConcurrentModificationException(); } }
        public void add(E e) { checkForComodification(); try { AbstractList.this.add(cursor, e); cursor++; lastRet = -1; expectedModCount = 0; } catch (IndexOutOfBoundsException ex) { throw new ConcurrentModificationException(); } }
    }
    
    private class SubList extends AbstractList<E> {
        private final AbstractList<E> parent;
        private final int offset;
        private int size;
        SubList(AbstractList<E> parent, int offset, int fromIndex, int toIndex) {
            this.parent = parent; this.offset = offset + fromIndex; this.size = toIndex - fromIndex;
        }
        public E get(int index) { return parent.get(offset + index); }
        public E set(int index, E element) { return parent.set(offset + index, element); }
        public void add(int index, E element) { parent.add(offset + index, element); this.size++; }
        public E remove(int index) { E result = parent.remove(offset + index); this.size--; return result; }
        protected void removeRange(int fromIndex, int toIndex) { parent.removeRange(offset + fromIndex, offset + toIndex); this.size -= toIndex - fromIndex; }
        public int size() { return size; }
        public List<E> subList(int fromIndex, int toIndex) { return new SubList(this, 0, fromIndex, toIndex); }
    }
}