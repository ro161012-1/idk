package java.util;

public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected AbstractMap() {}
    @Override public boolean isEmpty() { return size() == 0; }
    @Override public boolean containsValue(Object value) { return false; }
    @Override public boolean containsKey(Object key) { return false; }
    @Override public V get(Object key) { return null; }
    @Override public V put(K key, V value) { throw new UnsupportedOperationException(); }
    @Override public V remove(Object key) { throw new UnsupportedOperationException(); }
    @Override public void putAll(Map<? extends K, ? extends V> m) {}
    @Override public void clear() {}
    @Override public Set<K> keySet() { return null; }
    @Override public Collection<V> values() { return null; }
    @Override public Set<Entry<K, V>> entrySet() { return null; }
    @Override public boolean equals(Object o) { return false; }
    @Override public int hashCode() { return 0; }
    @Override public String toString() { return "{}"; }
}
