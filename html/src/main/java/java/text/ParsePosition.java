package java.text;

public class ParsePosition {
    private int index;
    private int errorIndex = -1;
    
    public ParsePosition(int index) { this.index = index; }
    
    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }
    public int getErrorIndex() { return errorIndex; }
    public void setErrorIndex(int errorIndex) { this.errorIndex = errorIndex; }
    @Override public boolean equals(Object obj) { return obj instanceof ParsePosition && index == ((ParsePosition)obj).index; }
    @Override public int hashCode() { return index; }
    @Override public String toString() { return "ParsePosition[index=" + index + ", errorIndex=" + errorIndex + "]"; }
}