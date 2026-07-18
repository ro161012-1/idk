package java.util;

public class HashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {
    private transient HashMap<E, Object> map;
    private static final Object PRESENT = new Object();
    
    public HashSet() { map = new HashMap<>(); }
    public HashSet(Collection<? extends E> c) { map = new HashMap<>(); addAll(c); }
    public HashSet(int initialCapacity) { map = new HashMap<>(initialCapacity); }
    public HashSet(int initialCapacity, float loadFactor) { map = new HashMap<>(initialCapacity, loadFactor); }
    
    @Override public Iterator<E> iterator() { return map.keySet().iterator(); }
    @Override public int size() { return map.size(); }
    @Override public boolean isEmpty() { return map.isEmpty(); }
    @Override public boolean contains(Object o) { return map.containsKey(o); }
    @Override public boolean add(E e) { return map.put(e, PRESENT) == null; }
    @Override public boolean remove(Object o) { return map.remove(o) == PRESENT; }
    @Override public void clear() { map.clear(); }
    @Override public Object clone() { try { HashSet<E> copy = (HashSet<E>) super.clone(); copy.map = (HashMap<E, Object>) map.clone(); return copy; } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
}
