package java.util;

public interface Map<K, V> {
    int size();
    boolean isEmpty();
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    V get(Object key);
    V put(K key, V value);
    V remove(Object key);
    void putAll(Map<? extends K, ? extends V> m);
    void clear();
    Set<K> keySet();
    Collection<V> values();
    Set<Entry<K, V>> entrySet();
    boolean equals(Object o);
    int hashCode();
    default V getOrDefault(Object key, V defaultValue) { return null; }
    default void forEach(BiConsumer<? super K, ? super V> action) {}
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {}
    default V putIfAbsent(K key, V value) { return null; }
    default boolean remove(Object key, Object value) { return false; }
    default boolean replace(K key, V oldValue, V newValue) { return false; }
    default V replace(K key, V value) { return null; }
    default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) { return null; }
    default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return null; }
    default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return null; }
    default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) { return null; }
    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
        boolean equals(Object o);
        int hashCode();
    }
    static <K, V> Entry<K, V> entry(K k, V v) { return null; }
}
