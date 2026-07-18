package java.util;

import java.io.Serializable;

public class TreeMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V>, Cloneable, Serializable {
    public TreeMap() {}
    public TreeMap(Comparator<? super K> comparator) {}
    public TreeMap(Map<? extends K, ? extends V> m) { putAll(m); }
    public TreeMap(SortedMap<K, ? extends V> m) { putAll(m); }
    
    @Override public int size() { return 0; }
    @Override public boolean isEmpty() { return true; }
    @Override public boolean containsKey(Object key) { return false; }
    @Override public boolean containsValue(Object value) { return false; }
    @Override public V get(Object key) { return null; }
    @Override public V put(K key, V value) { return null; }
    @Override public V remove(Object key) { return null; }
    @Override public void putAll(Map<? extends K, ? extends V> m) {}
    @Override public void clear() {}
    @Override public Set<K> keySet() { return null; }
    @Override public Collection<V> values() { return null; }
    @Override public Set<Entry<K, V>> entrySet() { return null; }
    @Override public Comparator<? super K> comparator() { return null; }
    @Override public K firstKey() { return null; }
    @Override public K lastKey() { return null; }
    @Override public Map.Entry<K, V> firstEntry() { return null; }
    @Override public Map.Entry<K, V> lastEntry() { return null; }
    @Override public Map.Entry<K, V> pollFirstEntry() { return null; }
    @Override public Map.Entry<K, V> pollLastEntry() { return null; }
    @Override public Map.Entry<K, V> lowerEntry(K key) { return null; }
    @Override public K lowerKey(K key) { return null; }
    @Override public Map.Entry<K, V> floorEntry(K key) { return null; }
    @Override public K floorKey(K key) { return null; }
    @Override public Map.Entry<K, V> ceilingEntry(K key) { return null; }
    @Override public K ceilingKey(K key) { return null; }
    @Override public Map.Entry<K, V> higherEntry(K key) { return null; }
    @Override public K higherKey(K key) { return null; }
    @Override public NavigableSet<K> navigableKeySet() { return null; }
    @Override public NavigableSet<K> descendingKeySet() { return null; }
    @Override public NavigableMap<K, V> descendingMap() { return null; }
    @Override public SortedMap<K, V> subMap(K fromKey, K toKey) { return null; }
    @Override public SortedMap<K, V> headMap(K toKey) { return null; }
    @Override public SortedMap<K, V> tailMap(K fromKey) { return null; }
    @Override public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) { return null; }
    @Override public NavigableMap<K, V> headMap(K toKey, boolean inclusive) { return null; }
    @Override public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) { return null; }
}