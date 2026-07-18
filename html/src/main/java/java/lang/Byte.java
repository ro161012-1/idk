package java.lang;

public final class Byte extends Number implements Comparable<Byte> {
    public static final byte MIN_VALUE = -128;
    public static final byte MAX_VALUE = 127;
    public static final int SIZE = 8;
    public static final int BYTES = 1;
    public static final Class<Byte> TYPE = byte.class;
    
    private final byte value;
    
    public Byte(byte value) { this.value = value; }
    public Byte(String s) throws NumberFormatException { this.value = parseByte(s); }
    
    public int intValue() { return value; }
    public long longValue() { return value; }
    public float floatValue() { return value; }
    public double doubleValue() { return value; }
    
    public static byte parseByte(String s) throws NumberFormatException { return parseByte(s, 10); }
    public static byte parseByte(String s, int radix) throws NumberFormatException { return (byte)Integer.parseInt(s, radix); }
    public static String toString(byte b) { return Integer.toString(b); }
    public static Byte valueOf(String s) { return valueOf(parseByte(s)); }
    public static Byte valueOf(byte b) { return new Byte(b); }
    
    public String toString() { return toString(value); }
    public int compareTo(Byte another) { return value - another.value; }
    public boolean equals(Object obj) { return obj instanceof Byte && value == ((Byte)obj).value; }
    public int hashCode() { return value; }
}
