package java.lang;

public final class StringBuilder implements Serializable, CharSequence {
    private char[] value;
    private int count;
    
    public StringBuilder() { this(16); }
    public StringBuilder(int capacity) { value = new char[capacity]; }
    public StringBuilder(String str) { value = new char[str.length() + 16]; append(str); }
    
    private void ensureCapacity(int minCapacity) { if (minCapacity > value.length) { char[] newValue = new char[Math.max(minCapacity, value.length * 2)]; System.arraycopy(value, 0, newValue, 0, count); value = newValue; } }
    
    public int length() { return count; }
    public char charAt(int index) { return value[index]; }
    public StringBuilder append(Object obj) { return append(String.valueOf(obj)); }
    public StringBuilder append(String str) { if (str == null) str = "null"; ensureCapacity(count + str.length()); str.getChars(0, str.length(), value, count); count += str.length(); return this; }
    public StringBuilder append(char c) { ensureCapacity(count + 1); value[count++] = c; return this; }
    public StringBuilder append(int i) { return append(Integer.toString(i)); }
    public StringBuilder append(long l) { return append(Long.toString(l)); }
    public StringBuilder append(float f) { return append(Float.toString(f)); }
    public StringBuilder append(double d) { return append(Double.toString(d)); }
    public StringBuilder append(boolean b) { return append(b ? "true" : "false"); }
    public StringBuilder append(char[] str) { ensureCapacity(count + str.length); System.arraycopy(str, 0, value, count, str.length); count += str.length; return this; }
    public StringBuilder append(char[] str, int offset, int len) { ensureCapacity(count + len); System.arraycopy(str, offset, value, count, len); count += len; return this; }
    
    public StringBuilder delete(int start, int end) { if (start < 0) throw new StringIndexOutOfBoundsException(start); if (end > count) end = count; if (start > end) throw new StringIndexOutOfBoundsException(); System.arraycopy(value, end, value, start, count - end); count -= end - start; return this; }
    public StringBuilder deleteCharAt(int index) { if (index < 0 || index >= count) throw new StringIndexOutOfBoundsException(index); System.arraycopy(value, index + 1, value, index, count - index - 1); count--; return this; }
    public StringBuilder insert(int offset, String str) { if (offset < 0 || offset > count) throw new StringIndexOutOfBoundsException(offset); if (str == null) str = "null"; ensureCapacity(count + str.length()); System.arraycopy(value, offset, value, offset + str.length(), count - offset); str.getChars(0, str.length(), value, offset); count += str.length(); return this; }
    
    public String toString() { return new String(value, 0, count); }
    public CharSequence subSequence(int start, int end) { return new String(value, start, end - start); }
}
