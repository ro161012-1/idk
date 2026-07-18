package java.util;

public class Arrays {
    public static <T> List<T> asList(T... a) { return new ArrayList<>(java.util.Arrays.asList(a)); }
    public static int binarySearch(int[] a, int key) { return binarySearch(a, 0, a.length, key); }
    public static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
        int low = fromIndex; int high = toIndex - 1;
        while (low <= high) { int mid = (low + high) >>> 1; int midVal = a[mid]; if (midVal < key) low = mid + 1; else if (midVal > key) high = mid - 1; else return mid; }
        return -(low + 1);
    }
    public static int binarySearch(Object[] a, Object key) { return binarySearch(a, 0, a.length, key); }
    public static int binarySearch(Object[] a, int fromIndex, int toIndex, Object key) { return -1; }
    public static boolean equals(int[] a, int[] a2) { if (a == a2) return true; if (a == null || a2 == null) return false; if (a.length != a2.length) return false; for (int i = 0; i < a.length; i++) if (a[i] != a2[i]) return false; return true; }
    public static boolean equals(Object[] a, Object[] a2) { if (a == a2) return true; if (a == null || a2 == null) return false; if (a.length != a2.length) return false; for (int i = 0; i < a.length; i++) if (!Objects.equals(a[i], a2[i])) return false; return true; }
    public static void fill(int[] a, int val) { for (int i = 0; i < a.length; i++) a[i] = val; }
    public static void fill(Object[] a, Object val) { for (int i = 0; i < a.length; i++) a[i] = val; }
    public static void fill(Object[] a, int fromIndex, int toIndex, Object val) { for (int i = fromIndex; i < toIndex; i++) a[i] = val; }
    public static String toString(int[] a) { if (a == null) return "null"; if (a.length == 0) return "[]"; StringBuilder sb = new StringBuilder(); sb.append('['); for (int i = 0; i < a.length; i++) { if (i > 0) sb.append(", "); sb.append(a[i]); } sb.append(']'); return sb.toString(); }
    public static String toString(Object[] a) { if (a == null) return "null"; if (a.length == 0) return "[]"; StringBuilder sb = new StringBuilder(); sb.append('['); for (int i = 0; i < a.length; i++) { if (i > 0) sb.append(", "); sb.append(a[i] == null ? "null" : a[i].toString()); } sb.append(']'); return sb.toString(); }
    public static <T> T[] copyOf(T[] original, int newLength) { return copyOf(original, newLength, (Class<T[]>)original.getClass()); }
    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) { @SuppressWarnings("unchecked") T[] copy = (T[]) java.lang.reflect.Array.newInstance(newType.getComponentType(), newLength); System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength)); return copy; }
    public static <T> T[] copyOfRange(T[] original, int from, int to) { int newLength = to - from; if (newLength < 0) throw new IllegalArgumentException(); @SuppressWarnings("unchecked") T[] copy = (T[]) java.lang.reflect.Array.newInstance(original.getClass().getComponentType(), newLength); System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength)); return copy; }
    public static void sort(Object[] a) { sort(a, 0, a.length, null); }
    public static void sort(Object[] a, int fromIndex, int toIndex) { sort(a, fromIndex, toIndex, null); }
    public static void sort(Object[] a, Comparator<? super Object> c) { sort(a, 0, a.length, c); }
    public static void sort(Object[] a, int fromIndex, int toIndex, Comparator<? super Object> c) { }
    public static void parallelSort(Object[] a) {}
    public static void parallelSort(Object[] a, int fromIndex, int toIndex) {}
    public static int hashCode(int[] a) { int result = 1; for (int element : a) result = 31 * result + element; return result; }
    public static int hashCode(Object[] a) { int result = 1; for (Object element : a) result = 31 * result + (element == null ? 0 : element.hashCode()); return result; }
}
