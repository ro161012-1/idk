package java.util;

public class Objects {
    public static boolean equals(Object a, Object b) { return a == b || (a != null && a.equals(b)); }
    public static int hashCode(Object o) { return o != null ? o.hashCode() : 0; }
    public static int hash(Object... values) { int result = 1; for (Object v : values) result = 31 * result + hashCode(v); return result; }
    public static boolean deepEquals(Object[] a, Object[] b) { if (a == b) return true; if (a == null || b == null) return false; if (a.length != b.length) return false; for (int i = 0; i < a.length; i++) if (!deepEquals(a[i], b[i])) return false; return true; }
    public static int deepHashCode(Object[] a) { if (a == null) return 0; int result = 1; for (Object e : a) result = 31 * result + (e == null ? 0 : e instanceof Object[] ? deepHashCode((Object[])e) : e.hashCode()); return result; }
    public static String toString(Object o) { return o != null ? o.toString() : "null"; }
    public static String toString(Object o, String nullDefault) { return o != null ? o.toString() : nullDefault; }
    public static <T> T requireNonNull(T obj) { if (obj == null) throw new NullPointerException(); return obj; }
    public static <T> T requireNonNull(T obj, String message) { if (obj == null) throw new NullPointerException(message); return obj; }
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) { if (obj == null) throw new NullPointerException(messageSupplier.get()); return obj; }
    public static <T> int compare(T a, T b, Comparator<? super T> c) { return c.compare(a, b); }
    public static boolean isNull(Object obj) { return obj == null; }
    public static boolean nonNull(Object obj) { return obj != null; }
}
