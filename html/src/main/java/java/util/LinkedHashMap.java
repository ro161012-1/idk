package java.util;

import java.io.Serializable;

public class LinkedHashMap<K, V> extends HashMap<K, V> {
    public LinkedHashMap() { super(); }
    public LinkedHashMap(int initialCapacity) { super(initialCapacity); }
    public LinkedHashMap(int initialCapacity, float loadFactor) { super(initialCapacity, loadFactor); }
    public LinkedHashMap(Map<? extends K, ? extends V> m) { super(m); }
    
    @Override
    public boolean containsValue(Object value) { return super.containsValue(value); }
}