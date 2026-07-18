package java.lang;

public final class Double extends Number implements Comparable<Double> {
    public static final double MIN_VALUE = 4.9E-324;
    public static final double MAX_VALUE = 1.7976931348623157E308;
    public static final double NaN = 0.0 / 0.0;
    public static final double POSITIVE_INFINITY = 1.0 / 0.0;
    public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final int SIZE = 64;
    public static final int BYTES = 8;
    public static final Class<Double> TYPE = double.class;
    
    private final double value;
    
    public Double(double value) { this.value = value; }
    public Double(String s) throws NumberFormatException { this.value = parseDouble(s); }
    
    public int intValue() { return (int)value; }
    public long longValue() { return (long)value; }
    public float floatValue() { return (float)value; }
    public double doubleValue() { return value; }
    
    public static double parseDouble(String s) throws NumberFormatException { return Double.parseDouble(s); }
    public static String toString(double d) { return Double.toString(d); }
    public static Double valueOf(String s) { return Double.valueOf(s); }
    public static Double valueOf(double d) { return new Double(d); }
    
    public String toString() { return toString(value); }
    public int compareTo(Double another) { return Double.compare(value, another.value); }
    public boolean equals(Object obj) { return obj instanceof Double && Double.doubleToLongBits(value) == Double.doubleToLongBits(((Double)obj).value); }
    public int hashCode() { long bits = Double.doubleToLongBits(value); return (int)(bits ^ (bits >>> 32)); }
    
    public static long doubleToLongBits(double value) { return Double.doubleToLongBits(value); }
    public static double longBitsToDouble(long bits) { return Double.longBitsToDouble(bits); }
    public static boolean isNaN(double v) { return Double.isNaN(v); }
    public static boolean isInfinite(double v) { return Double.isInfinite(v); }
    public boolean isNaN() { return isNaN(value); }
    public boolean isInfinite() { return isInfinite(value); }
}
