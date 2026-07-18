package java.text;

public abstract class NumberFormat extends Format {
    public static final int INTEGER_FIELD = 0;
    public static final int FRACTION_FIELD = 1;
    
    public static NumberFormat getInstance() { return new DecimalFormat(); }
    public static NumberFormat getInstance(Locale inLocale) { return new DecimalFormat(); }
    public static NumberFormat getNumberInstance() { return new DecimalFormat(); }
    public static NumberFormat getNumberInstance(Locale inLocale) { return new DecimalFormat(); }
    public static NumberFormat getIntegerInstance() { return new DecimalFormat(); }
    public static NumberFormat getIntegerInstance(Locale inLocale) { return new DecimalFormat(); }
    public static NumberFormat getCurrencyInstance() { return new DecimalFormat(); }
    public static NumberFormat getCurrencyInstance(Locale inLocale) { return new DecimalFormat(); }
    public static NumberFormat getPercentInstance() { return new DecimalFormat(); }
    public static NumberFormat getPercentInstance(Locale inLocale) { return new DecimalFormat(); }
    
    public abstract StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos);
    public abstract Number parse(String source, ParsePosition parsePosition);
    
    public String format(double number) { return format(number, new StringBuffer(), new FieldPosition(0)).toString(); }
    public String format(long number) { return format(number, new StringBuffer(), new FieldPosition(0)).toString(); }
    public String format(Object number) { return format(number, new StringBuffer(), new FieldPosition(0)).toString(); }
    public Number parse(String source) throws ParseException { ParsePosition pos = new ParsePosition(0); Number result = parse(source, pos); if (pos.index == 0) throw new ParseException("Unparseable number: " + source, 0); return result; }
    public Object parseObject(String source, ParsePosition pos) { return parse(source, pos); }
    
    public boolean isGroupingUsed() { return false; }
    public void setGroupingUsed(boolean newValue) {}
    public int getMaximumIntegerDigits() { return 0; }
    public void setMaximumIntegerDigits(int newValue) {}
    public int getMinimumIntegerDigits() { return 0; }
    public void setMinimumIntegerDigits(int newValue) {}
    public int getMaximumFractionDigits() { return 0; }
    public void setMaximumFractionDigits(int newValue) {}
    public int getMinimumFractionDigits() { return 0; }
    public void setMinimumFractionDigits(int newValue) {}
    public int getRoundingMode() { return 0; }
    public void setRoundingMode(int roundingMode) {}
    
    public static NumberFormat getCompactNumberInstance() { return new DecimalFormat(); }
    public static NumberFormat getCompactNumberInstance(Locale locale) { return new DecimalFormat(); }
}