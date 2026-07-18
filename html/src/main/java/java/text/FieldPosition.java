package java.text;

public class FieldPosition {
    private final int field;
    private int beginIndex;
    private int endIndex;
    
    public FieldPosition(int field) { this.field = field; }
    public FieldPosition(Format.Field attribute) { this.field = 0; }
    
    public int getField() { return field; }
    public int getBeginIndex() { return beginIndex; }
    public int getEndIndex() { return endIndex; }
    public void setBeginIndex(int bi) { beginIndex = bi; }
    public void setEndIndex(int ei) { endIndex = ei; }
    @Override public boolean equals(Object obj) { return obj instanceof FieldPosition && field == ((FieldPosition)obj).field; }
    @Override public int hashCode() { return field; }
    @Override public String toString() { return "FieldPosition[field=" + field + ", begin=" + beginIndex + ", end=" + endIndex + "]"; }
}