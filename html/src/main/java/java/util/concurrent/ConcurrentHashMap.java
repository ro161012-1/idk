package java.util.concurrent;

import java.util.*;

/**
 * GWT emulation of ConcurrentHashMap - delegates to HashMap (single-threaded in GWT).
 */
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
    private final Map<K, V> delegate = new HashMap<>();
    
    @Override
    public int size() { return delegate.size(); }
    
    @Override
    public boolean isEmpty() { return delegate.isEmpty(); }
    
    @Override
    public V get(Object key) { return delegate.get(key); }
    
    @Override
    public boolean containsKey(Object key) { return delegate.containsKey(key); }
    
    @Override
    public V put(K key, V value) { return delegate.put(key, value); }
    
    @Override
    public V remove(Object key) { return delegate.remove(key); }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m) { delegate.putAll(m); }
    
    @Override
    public void clear() { delegate.clear(); }
    
    @Override
    public Set<K> keySet() { return delegate.keySet(); }
    
    @Override
    public Collection<V> values() { return delegate.values(); }
    
    @Override
    public Set<Entry<K, V>> entrySet() { return delegate.entrySet(); }
    
    @Override
    public V putIfAbsent(K key, V value) { return delegate.putIfAbsent(key, value); }
    
    @Override
    public boolean remove(Object key, Object value) { 
        V cur = delegate.get(key);
        if (cur != null && cur.equals(value)) {
            delegate.remove(key);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        V cur = delegate.get(key);
        if (cur != null && cur.equals(oldValue)) {
            delegate.put(key, newValue);
            return true;
        }
        return false;
    }
    
    @Override
    public V replace(K key, V value) { return delegate.replace(key, value); }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) { return delegate.getOrDefault(key, defaultValue); }
    
    @Override
    public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) { delegate.forEach(action); }
    
    @Override
    public void replaceAll(java.util.function.BiFunction<? super K, ? super V, ? extends V> function) { delegate.replaceAll(function); }
    
    @Override
    public V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) { return delegate.computeIfAbsent(key, mappingFunction); }
    
    @Override
    public V computeIfPresent(K key, java.util.function.BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return delegate.computeIfPresent(key, remappingFunction); }
    
    @Override
    public V compute(K key, java.util.function.BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return delegate.compute(key, remappingFunction); }
    
    @Override
    public V merge(K key, V value, java.util.function.BiFunction<? super V, ? super V, ? extends V> remappingFunction) { return delegate.merge(key, value, remappingFunction); }
}