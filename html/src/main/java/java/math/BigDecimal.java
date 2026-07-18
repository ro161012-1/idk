package java.math;

public class BigDecimal extends Number implements Comparable<BigDecimal> {
    private final double value;
    private final int scale;
    
    public BigDecimal(String val) { this.value = Double.parseDouble(val); this.scale = 0; }
    public BigDecimal(double val) { this.value = val; this.scale = 0; }
    public BigDecimal(BigInteger val) { this.value = val.doubleValue(); this.scale = 0; }
    public BigDecimal(BigInteger unscaledVal, int scale) { this.value = unscaledVal.doubleValue(); this.scale = scale; }
    
    public static BigDecimal valueOf(double val) { return new BigDecimal(val); }
    public static BigDecimal valueOf(long val) { return new BigDecimal(val); }
    public static BigDecimal valueOf(long unscaledVal, int scale) { return new BigDecimal(unscaledVal); }
    public static BigDecimal ZERO = new BigDecimal(0);
    public static BigDecimal ONE = new BigDecimal(1);
    public static BigDecimal TEN = new BigDecimal(10);
    
    public BigDecimal add(BigDecimal val) { return new BigDecimal(value + val.value); }
    public BigDecimal subtract(BigDecimal val) { return new BigDecimal(value - val.value); }
    public BigDecimal multiply(BigDecimal val) { return new BigDecimal(value * val.value); }
    public BigDecimal divide(BigDecimal val) { return new BigDecimal(value / val.value); }
    public BigDecimal abs() { return value >= 0 ? this : new BigDecimal(-value); }
    public BigDecimal negate() { return new BigDecimal(-value); }
    public int signum() { return value > 0 ? 1 : value < 0 ? -1 : 0; }
    public int compareTo(BigDecimal val) { return Double.compare(value, val.value); }
    public boolean equals(Object x) { return x instanceof BigDecimal && value == ((BigDecimal)x).value; }
    public int hashCode() { return Double.hashCode(value); }
    public String toString() { return Double.toString(value); }
    public String toPlainString() { return Double.toString(value); }
    
    public BigDecimal setScale(int newScale) { return new BigDecimal(value); }
    public BigDecimal setScale(int newScale, int roundingMode) { return new BigDecimal(value); }
    public int scale() { return scale; }
    public int precision() { return Double.toString(value).length(); }
    
    @Override public int intValue() { return (int)value; }
    @Override public long longValue() { return (long)value; }
    @Override public float floatValue() { return (float)value; }
    @Override public double doubleValue() { return value; }
}