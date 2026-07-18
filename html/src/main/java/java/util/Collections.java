package java.util;

public class Collections {
    public static <T> void sort(List<T> list) { sort(list, null); }
    public static <T> void sort(List<T> list, Comparator<? super T> c) { list.sort(c); }
    public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) { return binarySearch(list, key, null); }
    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) { return -1; }
    public static void reverse(List<?> list) { for (int i = 0, j = list.size() - 1; i < j; i++, j--) swap(list, i, j); }
    public static void shuffle(List<?> list) { shuffle(list, new Random()); }
    public static void shuffle(List<?> list, Random rnd) { for (int i = list.size(); i > 1; i--) swap(list, i - 1, rnd.nextInt(i)); }
    public static void swap(List<?> list, int i, int j) { Object tmp = list.get(i); list.set(i, list.get(j)); list.set(j, tmp); }
    public static <T> void fill(List<? super T> list, T obj) { for (int i = 0; i < list.size(); i++) list.set(i, obj); }
    public static <T> void copy(List<? super T> dest, List<? extends T> src) { for (int i = 0; i < src.size(); i++) dest.set(i, src.get(i)); }
    public static <T> boolean addAll(Collection<? super T> c, T... elements) { boolean modified = false; for (T e : elements) if (c.add(e)) modified = true; return modified; }
    public static <T> boolean replaceAll(List<T> list, T oldVal, T newVal) { boolean modified = false; for (int i = 0; i < list.size(); i++) if (java.util.Objects.equals(list.get(i), oldVal)) { list.set(i, newVal); modified = true; } return modified; }
    public static <T> int indexOfSubList(List<?> source, List<?> target) { return -1; }
    public static <T> int lastIndexOfSubList(List<?> source, List<?> target) { return -1; }
    public static <T> List<T> unmodifiableList(List<? extends T> list) { return list; }
    public static <T> Set<T> unmodifiableSet(Set<? extends T> set) { return set; }
    public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) { return map; }
    public static <T> List<T> synchronizedList(List<T> list) { return list; }
    public static <T> Set<T> synchronizedSet(Set<T> set) { return set; }
    public static <K, V> Map<K, V> synchronizedMap(Map<K, V> map) { return map; }
    public static <T> List<T> emptyList() { return new ArrayList<>(); }
    public static <T> Set<T> emptySet() { return new HashSet<>(); }
    public static <K, V> Map<K, V> emptyMap() { return new HashMap<>(); }
    public static <T> List<T> singletonList(T o) { List<T> list = new ArrayList<>(); list.add(o); return list; }
    public static <T> Set<T> singletonSet(T o) { Set<T> set = new HashSet<>(); set.add(o); return set; }
    public static <K, V> Map<K, V> singletonMap(K key, V value) { Map<K, V> map = new HashMap<>(); map.put(key, value); return map; }
    public static <T> List<T> nCopies(int n, T o) { List<T> list = new ArrayList<>(n); for (int i = 0; i < n; i++) list.add(o); return list; }
    public static <T> Enumeration<T> enumeration(Collection<T> c) { return new Enumeration<T>() { Iterator<T> it = c.iterator(); public boolean hasMoreElements() { return it.hasNext(); } public T nextElement() { return it.next(); } }; }
    public static <T> ArrayList<T> list(Enumeration<T> e) { ArrayList<T> list = new ArrayList<>(); while (e.hasMoreElements()) list.add(e.nextElement()); return list; }
    public static int frequency(Collection<?> c, Object o) { int count = 0; for (Object e : c) if (java.util.Objects.equals(e, o)) count++; return count; }
    public static boolean disjoint(Collection<?> c1, Collection<?> c2) { for (Object e : c1) if (c2.contains(e)) return false; return true; }
    public static <T> T max(Collection<? extends T> coll) { return max(coll, null); }
    public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) { return null; }
    public static <T> T min(Collection<? extends T> coll) { return min(coll, null); }
    public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) { return null; }
    public static <T> Comparator<T> reverseOrder() { return (a, b) -> b.compareTo(a); }
    public static <T> Comparator<T> reverseOrder(Comparator<T> cmp) { return (a, b) -> cmp.compare(b, a); }
    public static <T extends Comparable<? super T>> List<T> synchronizedSortedList(List<T> list) { return list; }
}
