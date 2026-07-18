package java.util;

public class TreeSet<E> extends AbstractSet<E> implements NavigableSet<E>, Cloneable, Serializable {
    private transient NavigableMap<E, Object> m;
    private static final Object PRESENT = new Object();
    
    public TreeSet() { this.m = new TreeMap<>(); }
    public TreeSet(Comparator<? super E> comparator) { this.m = new TreeMap<>(comparator); }
    public TreeSet(Collection<? extends E> c) { this(); addAll(c); }
    public TreeSet(SortedSet<E> s) { this.m = new TreeMap<>(); addAll(s); }
    
    @Override public Iterator<E> iterator() { return m.navigableKeySet().iterator(); }
    @Override public int size() { return m.size(); }
    @Override public boolean isEmpty() { return m.isEmpty(); }
    @Override public boolean contains(Object o) { return m.containsKey(o); }
    @Override public boolean add(E e) { return m.put(e, PRESENT) == null; }
    @Override public boolean remove(Object o) { return m.remove(o) == PRESENT; }
    @Override public void clear() { m.clear(); }
    @Override public NavigableSet<E> descendingSet() { return m.descendingMap().navigableKeySet(); }
    @Override public Iterator<E> descendingIterator() { return descendingSet().iterator(); }
    @Override public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) { return new TreeSet<>(m.subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet()); }
    @Override public NavigableSet<E> headSet(E toElement, boolean inclusive) { return new TreeSet<>(m.headMap(toElement, inclusive).navigableKeySet()); }
    @Override public NavigableSet<E> tailSet(E fromElement, boolean inclusive) { return new TreeSet<>(m.tailMap(fromElement, inclusive).navigableKeySet()); }
    @Override public SortedSet<E> subSet(E fromElement, E toElement) { return subSet(fromElement, true, toElement, false); }
    @Override public SortedSet<E> headSet(E toElement) { return headSet(toElement, false); }
    @Override public SortedSet<E> tailSet(E fromElement) { return tailSet(fromElement, true); }
    @Override public Comparator<? super E> comparator() { return m.comparator(); }
    @Override public E first() { return m.firstKey(); }
    @Override public E last() { return m.lastKey(); }
    @Override public E lower(E e) { return m.lowerKey(e); }
    @Override public E floor(E e) { return m.floorKey(e); }
    @Override public E ceiling(E e) { return m.ceilingKey(e); }
    @Override public E higher(E e) { return m.higherKey(e); }
    @Override public E pollFirst() { Map.Entry<E, Object> e = m.pollFirstEntry(); return e == null ? null : e.getKey(); }
    @Override public E pollLast() { Map.Entry<E, Object> e = m.pollLastEntry(); return e == null ? null : e.getKey(); }
    @Override public Object clone() { try { TreeSet<E> copy = (TreeSet<E>) super.clone(); copy.m = new TreeMap<>(m); return copy; } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
}