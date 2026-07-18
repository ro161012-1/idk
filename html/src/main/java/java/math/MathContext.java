package java.math;

public class MathContext implements Serializable {
    public static final MathContext UNLIMITED = new MathContext(0, RoundingMode.HALF_UP);
    public static final MathContext DECIMAL32 = new MathContext(7, RoundingMode.HALF_UP);
    public static final MathContext DECIMAL64 = new MathContext(16, RoundingMode.HALF_UP);
    public static final MathContext DECIMAL128 = new MathContext(34, RoundingMode.HALF_UP);
    
    private final int precision;
    private final RoundingMode roundingMode;
    
    public MathContext(int setPrecision) { this(setPrecision, RoundingMode.HALF_UP); }
    public MathContext(int setPrecision, RoundingMode setRoundingMode) { this.precision = setPrecision; this.roundingMode = setRoundingMode; }
    
    public int getPrecision() { return precision; }
    public RoundingMode getRoundingMode() { return roundingMode; }
    public boolean equals(Object x) { return x instanceof MathContext && precision == ((MathContext)x).precision && roundingMode == ((MathContext)x).roundingMode; }
    public int hashCode() { return precision * 31 + roundingMode.hashCode(); }
    public String toString() { return "precision=" + precision + " roundingMode=" + roundingMode; }
}