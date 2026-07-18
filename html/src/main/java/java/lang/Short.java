package java.lang;

public final class Short extends Number implements Comparable<Short> {
    public static final short MIN_VALUE = -32768;
    public static final short MAX_VALUE = 32767;
    public static final int SIZE = 16;
    public static final int BYTES = 2;
    public static final Class<Short> TYPE = short.class;
    
    private final short value;
    
    public Short(short value) { this.value = value; }
    public Short(String s) throws NumberFormatException { this.value = parseShort(s); }
    
    public int intValue() { return value; }
    public long longValue() { return value; }
    public float floatValue() { return value; }
    public double doubleValue() { return value; }
    
    public static short parseShort(String s) throws NumberFormatException { return parseShort(s, 10); }
    public static short parseShort(String s, int radix) throws NumberFormatException { return (short)Integer.parseInt(s, radix); }
    public static String toString(short s) { return Integer.toString(s); }
    public static Short valueOf(String s) { return valueOf(parseShort(s)); }
    public static Short valueOf(short s) { return new Short(s); }
    
    public String toString() { return toString(value); }
    public int compareTo(Short another) { return value - another.value; }
    public boolean equals(Object obj) { return obj instanceof Short && value == ((Short)obj).value; }
    public int hashCode() { return value; }
}
