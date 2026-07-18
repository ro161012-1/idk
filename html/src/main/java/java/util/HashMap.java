package java.util;

public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    private transient Node<K, V>[] table;
    private transient int size;
    private int threshold;
    private final float loadFactor;
    
    static class Node<K, V> implements Entry<K, V> {
        final int hash; final K key; V value; Node<K, V> next;
        Node(int hash, K key, V value, Node<K, V> next) { this.hash = hash; this.key = key; this.value = value; this.next = next; }
        public K getKey() { return key; }
        public V getValue() { return value; }
        public V setValue(V newValue) { V old = value; value = newValue; return old; }
        public boolean equals(Object o) { return o instanceof Entry && key.equals(((Entry)o).getKey()) && value.equals(((Entry)o).getValue()); }
        public int hashCode() { return key.hashCode() ^ value.hashCode(); }
        public String toString() { return key + "=" + value; }
    }
    
    public HashMap() { this.loadFactor = 0.75f; }
    public HashMap(int initialCapacity) { this(initialCapacity, 0.75f); }
    public HashMap(int initialCapacity, float loadFactor) { this.loadFactor = loadFactor; }
    public HashMap(Map<? extends K, ? extends V> m) { this.loadFactor = 0.75f; putAll(m); }
    
    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public V get(Object key) { return null; }
    @Override public boolean containsKey(Object key) { return false; }
    @Override public V put(K key, V value) { return null; }
    @Override public V remove(Object key) { return null; }
    @Override public void putAll(Map<? extends K, ? extends V> m) {}
    @Override public void clear() {}
    @Override public Set<K> keySet() { return null; }
    @Override public Collection<V> values() { return null; }
    @Override public Set<Entry<K, V>> entrySet() { return null; }
}
