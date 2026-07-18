package java.lang;

public final class Float extends Number implements Comparable<Float> {
    public static final float MIN_VALUE = 1.4E-45f;
    public static final float MAX_VALUE = 3.4028235E38f;
    public static final float NaN = 0.0f / 0.0f;
    public static final float POSITIVE_INFINITY = 1.0f / 0.0f;
    public static final float NEGATIVE_INFINITY = -1.0f / 0.0f;
    public static final int SIZE = 32;
    public static final int BYTES = 4;
    public static final Class<Float> TYPE = float.class;
    
    private final float value;
    
    public Float(float value) { this.value = value; }
    public Float(String s) throws NumberFormatException { this.value = parseFloat(s); }
    
    public int intValue() { return (int)value; }
    public long longValue() { return (long)value; }
    public float floatValue() { return value; }
    public double doubleValue() { return value; }
    
    public static float parseFloat(String s) throws NumberFormatException { return Float.parseFloat(s); }
    public static String toString(float f) { return Float.toString(f); }
    public static Float valueOf(String s) { return Float.valueOf(s); }
    public static Float valueOf(float f) { return new Float(f); }
    
    public String toString() { return toString(value); }
    public int compareTo(Float another) { return Float.compare(value, another.value); }
    public boolean equals(Object obj) { return obj instanceof Float && Float.floatToIntBits(value) == Float.floatToIntBits(((Float)obj).value); }
    public int hashCode() { return Float.floatToIntBits(value); }
    
    public static int floatToIntBits(float value) { return Float.floatToIntBits(value); }
    public static float intBitsToFloat(int bits) { return Float.intBitsToFloat(bits); }
    public static boolean isNaN(float v) { return Float.isNaN(v); }
    public static boolean isInfinite(float v) { return Float.isInfinite(v); }
    public boolean isNaN() { return isNaN(value); }
    public boolean isInfinite() { return isInfinite(value); }
}
