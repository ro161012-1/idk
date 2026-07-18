package java.lang;

public final class Boolean implements Serializable, Comparable<Boolean> {
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);
    public static final Class<Boolean> TYPE = boolean.class;
    
    private final boolean value;
    
    public Boolean(boolean value) { this.value = value; }
    public Boolean(String s) { this.value = "true".equalsIgnoreCase(s); }
    
    public boolean booleanValue() { return value; }
    public static boolean parseBoolean(String s) { return "true".equalsIgnoreCase(s); }
    public static Boolean valueOf(boolean b) { return b ? TRUE : FALSE; }
    public static Boolean valueOf(String s) { return valueOf(parseBoolean(s)); }
    
    public String toString() { return value ? "true" : "false"; }
    public int compareTo(Boolean b) { return value == b.value ? 0 : value ? 1 : -1; }
    public boolean equals(Object obj) { return obj instanceof Boolean && value == ((Boolean)obj).value; }
    public int hashCode() { return value ? 1231 : 1237; }
    
    public static int compare(boolean x, boolean y) { return (x == y) ? 0 : x ? 1 : -1; }
    public static boolean logicalAnd(boolean a, boolean b) { return a && b; }
    public static boolean logicalOr(boolean a, boolean b) { return a || b; }
    public static boolean logicalXor(boolean a, boolean b) { return a ^ b; }
}
