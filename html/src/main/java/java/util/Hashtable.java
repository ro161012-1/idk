package java.util;

public class Hashtable<K, V> extends Dictionary<K, V> implements Map<K, V>, Cloneable, Serializable {
    public Hashtable() {}
    public Hashtable(int initialCapacity) {}
    public Hashtable(int initialCapacity, float loadFactor) {}
    public Hashtable(Map<? extends K, ? extends V> t) {}
    
    @Override public int size() { return 0; }
    @Override public boolean isEmpty() { return true; }
    @Override public V get(Object key) { return null; }
    @Override public boolean containsKey(Object key) { return false; }
    @Override public V put(K key, V value) { return null; }
    @Override public V remove(Object key) { return null; }
    @Override public void clear() {}
    @Override public Set<K> keySet() { return null; }
    @Override public Collection<V> values() { return null; }
    @Override public Set<Entry<K, V>> entrySet() { return null; }
    public synchronized Enumeration<K> keys() { return null; }
    public synchronized Enumeration<V> elements() { return null; }
}
