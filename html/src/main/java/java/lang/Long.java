package java.lang;

public final class Long extends Number implements Comparable<Long> {
    public static final long MIN_VALUE = -9223372036854775808L;
    public static final long MAX_VALUE = 9223372036854775807L;
    public static final int SIZE = 64;
    public static final int BYTES = 8;
    public static final Class<Long> TYPE = long.class;
    
    private final long value;
    
    public Long(long value) { this.value = value; }
    public Long(String s) throws NumberFormatException { this.value = parseLong(s); }
    
    public int intValue() { return (int)value; }
    public long longValue() { return value; }
    public float floatValue() { return value; }
    public double doubleValue() { return value; }
    
    public static long parseLong(String s) throws NumberFormatException { return parseLong(s, 10); }
    public static long parseLong(String s, int radix) throws NumberFormatException { return Long.parseLong(s, radix); }
    
    public static String toString(long i) { return Long.toString(i); }
    public static String toString(long i, int radix) { return Long.toString(i, radix); }
    public static Long valueOf(String s) { return Long.valueOf(s); }
    public static Long valueOf(long l) { return new Long(l); }
    
    public String toString() { return toString(value); }
    public int compareTo(Long another) { return Long.compare(value, another.value); }
    public boolean equals(Object obj) { return obj instanceof Long && value == ((Long)obj).value; }
    public int hashCode() { return (int)(value ^ (value >>> 32)); }
    
    public static long min(long a, long b) { return a < b ? a : b; }
    public static long max(long a, long b) { return a > b ? a : b; }
    public static long sum(long a, long b) { return a + b; }
    public static int compare(long x, long y) { return (x < y) ? -1 : ((x == y) ? 0 : 1); }
    public static int bitCount(long i) { return Long.bitCount(i); }
    public static int signum(long i) { return (int)((i >> 63) | (-i >>> 63)); }
}
