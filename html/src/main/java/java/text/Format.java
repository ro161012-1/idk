package java.text;

public abstract class Format implements Serializable, Cloneable {
    public static class Field {
        private final String name;
        public Field(String name) { this.name = name; }
        public String getName() { return name; }
        @Override public String toString() { return "Field[" + name + "]"; }
    }
    
    public abstract StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos);
    public abstract Object parseObject(String source, ParsePosition pos);
    
    public final String format(Object obj) { return format(obj, new StringBuffer(), new FieldPosition(0)).toString(); }
    public Object parseObject(String source) throws ParseException { ParsePosition pos = new ParsePosition(0); Object result = parseObject(source, pos); if (pos.index == 0) throw new ParseException("Unparseable: " + source, 0); return result; }
    
    public Object parseObject(String source, ParsePosition pos) { return parseObject(source, pos); }
    
    public Object clone() { try { return super.clone(); } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
}