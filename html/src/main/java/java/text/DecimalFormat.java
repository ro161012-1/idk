package java.text;

public class DecimalFormat extends NumberFormat {
    private String pattern;
    private DecimalFormatSymbols symbols;
    
    public DecimalFormat() { this("#,##0.###"); }
    public DecimalFormat(String pattern) { this.pattern = pattern; this.symbols = new DecimalFormatSymbols(); }
    public DecimalFormat(String pattern, DecimalFormatSymbols symbols) { this.pattern = pattern; this.symbols = symbols; }
    
    @Override public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
        String s = number.toString();
        toAppendTo.append(s);
        return toAppendTo;
    }
    
    @Override public Number parse(String source, ParsePosition parsePosition) {
        try { return Double.valueOf(source); } catch (NumberFormatException e) { return null; }
    }
    
    public void applyPattern(String pattern) { this.pattern = pattern; }
    public String toPattern() { return pattern; }
    public String toLocalizedPattern() { return pattern; }
    public void setSymbols(DecimalFormatSymbols symbols) { this.symbols = symbols; }
    public DecimalFormatSymbols getSymbols() { return symbols; }
    public void setDecimalSeparator(char decimalSeparator) { symbols.setDecimalSeparator(decimalSeparator); }
    public char getDecimalSeparator() { return symbols.getDecimalSeparator(); }
    public void setGroupingSeparator(char groupingSeparator) { symbols.setGroupingSeparator(groupingSeparator); }
    public char getGroupingSeparator() { return symbols.getGroupingSeparator(); }
    public void setMinusSign(char minusSign) { symbols.setMinusSign(minusSign); }
    public char getMinusSign() { return symbols.getMinusSign(); }
}