package java.lang;

public final class Integer extends Number implements Comparable<Integer> {
    public static final int MIN_VALUE = -2147483648;
    public static final int MAX_VALUE = 2147483647;
    public static final int SIZE = 32;
    public static final int BYTES = 4;
    public static final Class<Integer> TYPE = int.class;
    
    private final int value;
    
    public Integer(int value) { this.value = value; }
    public Integer(String s) throws NumberFormatException { this.value = parseInt(s); }
    
    public int intValue() { return value; }
    public long longValue() { return value; }
    public float floatValue() { return value; }
    public double doubleValue() { return value; }
    
    public static int parseInt(String s) throws NumberFormatException { return parseInt(s, 10); }
    public static int parseInt(String s, int radix) throws NumberFormatException {
        if (s == null) throw new NumberFormatException("null");
        int result = 0;
        boolean negative = false;
        int i = 0;
        if (s.charAt(0) == '-') { negative = true; i = 1; }
        else if (s.charAt(0) == '+') { i = 1; }
        for (; i < s.length(); i++) {
            char c = s.charAt(i);
            int digit = c >= '0' && c <= '9' ? c - '0' : c >= 'a' && c <= 'z' ? c - 'a' + 10 : c >= 'A' && c <= 'Z' ? c - 'A' + 10 : -1;
            if (digit < 0 || digit >= radix) throw new NumberFormatException(s);
            result = result * radix + digit;
        }
        return negative ? -result : result;
    }
    
    public static String toString(int i) { return toString(i, 10); }
    public static String toString(int i, int radix) {
        if (i == 0) return "0";
        boolean negative = i < 0;
        if (negative) i = -i;
        char[] buf = new char[32];
        int pos = 32;
        while (i != 0) { int d = i % radix; buf[--pos] = (char)(d < 10 ? '0' + d : 'a' + d - 10); i /= radix; }
        if (negative) buf[--pos] = '-';
        return new String(buf, pos, 32 - pos);
    }
    
    public static int valueOf(String s) { return parseInt(s); }
    public static Integer valueOf(int i) { return new Integer(i); }
    
    public String toString() { return toString(value); }
    public int compareTo(Integer another) { return value - another.value; }
    public boolean equals(Object obj) { return obj instanceof Integer && value == ((Integer)obj).value; }
    public int hashCode() { return value; }
    
    public static int min(int a, int b) { return a < b ? a : b; }
    public static int max(int a, int b) { return a > b ? a : b; }
    public static int sum(int a, int b) { return a + b; }
    public static int compare(int x, int y) { return (x < y) ? -1 : ((x == y) ? 0 : 1); }
    public static int highestOneBit(int i) { return i & (1 << (31 - numberOfLeadingZeros(i))); }
    public static int lowestOneBit(int i) { return i & -i; }
    public static int numberOfLeadingZeros(int i) { if (i == 0) return 32; int n = 1; if ((i >>> 16) == 0) { n += 16; i <<= 16; } if ((i >>> 24) == 0) { n += 8; i <<= 8; } if ((i >>> 28) == 0) { n += 4; i <<= 4; } if ((i >>> 30) == 0) { n += 2; i <<= 2; } n -= i >>> 31; return n; }
    public static int numberOfTrailingZeros(int i) { if (i == 0) return 32; int n = 31; int y = i << 16; if (y != 0) { n -= 16; i = y; } y = i << 8; if (y != 0) { n -= 8; i = y; } y = i << 4; if (y != 0) { n -= 4; i = y; } y = i << 2; if (y != 0) { n -= 2; i = y; } return n - ((i << 1) >>> 31); }
    public static int bitCount(int i) { i = i - ((i >>> 1) & 0x55555555); i = (i & 0x33333333) + ((i >>> 2) & 0x33333333); i = (i + (i >>> 4)) & 0x0f0f0f0f; i = i + (i >>> 8); i = i + (i >>> 16); return i & 0x3f; }
    public static int rotateLeft(int i, int distance) { return (i << distance) | (i >>> -distance); }
    public static int rotateRight(int i, int distance) { return (i >>> distance) | (i << -distance); }
    public static int reverse(int i) { i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555; i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333; i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f; i = (i & 0x00ff00ff) << 8 | (i >>> 8) & 0x00ff00ff; return (i << 16) | (i >>> 16); }
    public static int signum(int i) { return (i >> 31) | (-i >>> 31); }
    public static int reverseBytes(int i) { return (i >>> 24) | ((i << 8) & 0x00FF0000) | ((i >> 8) & 0x0000FF00) | (i << 24); }
}
