package java.util;

public interface SortedMap<K, V> extends Map<K, V> {
    Comparator<? super K> comparator();
    K firstKey();
    K lastKey();
    SortedMap<K, V> headMap(K toKey);
    SortedMap<K, V> tailMap(K fromKey);
    SortedMap<K, V> subMap(K fromKey, K toKey);
    Set<K> keySet();
    Collection<V> values();
    Set<Entry<K, V>> entrySet();
}