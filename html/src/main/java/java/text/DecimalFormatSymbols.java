package java.text;

public class DecimalFormatSymbols implements Cloneable, Serializable {
    private char decimalSeparator = '.';
    private char groupingSeparator = ',';
    private char minusSign = '-';
    private char percent = '%';
    private char perMill = '\u2030';
    private char zeroDigit = '0';
    private char digit = '#';
    private char patternSeparator = ';';
    private String infinity = "Infinity";
    private String NaN = "NaN";
    private String monetarySeparator = ',';
    private char exponential = 'E';
    
    public DecimalFormatSymbols() { this(Locale.getDefault()); }
    public DecimalFormatSymbols(Locale locale) {}
    
    public char getDecimalSeparator() { return decimalSeparator; }
    public void setDecimalSeparator(char decimalSeparator) { this.decimalSeparator = decimalSeparator; }
    public char getGroupingSeparator() { return groupingSeparator; }
    public void setGroupingSeparator(char groupingSeparator) { this.groupingSeparator = groupingSeparator; }
    public char getMinusSign() { return minusSign; }
    public void setMinusSign(char minusSign) { this.minusSign = minusSign; }
    public char getPercent() { return percent; }
    public void setPercent(char percent) { this.percent = percent; }
    public char getPerMill() { return perMill; }
    public void setPerMill(char perMill) { this.perMill = perMill; }
    public char getZeroDigit() { return zeroDigit; }
    public void setZeroDigit(char zeroDigit) { this.zeroDigit = zeroDigit; }
    public char getDigit() { return digit; }
    public void setDigit(char digit) { this.digit = digit; }
    public char getPatternSeparator() { return patternSeparator; }
    public void setPatternSeparator(char patternSeparator) { this.patternSeparator = patternSeparator; }
    public String getInfinity() { return infinity; }
    public void setInfinity(String infinity) { this.infinity = infinity; }
    public String getNaN() { return NaN; }
    public void setNaN(String NaN) { this.NaN = NaN; }
    public String getMonetarySeparator() { return monetarySeparator; }
    public void setMonetarySeparator(String monetarySeparator) { this.monetarySeparator = monetarySeparator; }
    public char getExponentSeparator() { return exponential; }
    public void setExponentSeparator(char exponential) { this.exponential = exponential; }
    
    @Override public boolean equals(Object obj) { return obj instanceof DecimalFormatSymbols && decimalSeparator == ((DecimalFormatSymbols)obj).decimalSeparator; }
    @Override public int hashCode() { return decimalSeparator; }
    @Override public Object clone() { try { return super.clone(); } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
}