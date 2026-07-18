package java.lang;

public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    private final char[] value;
    private final int hash;
    
    public String() { this.value = new char[0]; this.hash = 0; }
    public String(String original) { this.value = original.value; this.hash = original.hash; }
    public String(char[] value) { this.value = value; this.hash = 0; }
    public String(char[] value, int offset, int count) { this.value = new char[count]; System.arraycopy(value, offset, this.value, 0, count); this.hash = 0; }
    public String(byte[] bytes) { this.value = new String(bytes).value; this.hash = 0; }
    public String(byte[] bytes, int offset, int length) { this.value = new String(bytes, offset, length).value; this.hash = 0; }
    
    public int length() { return value.length; }
    public char charAt(int index) { return value[index]; }
    public CharSequence subSequence(int start, int end) { return substring(start, end); }
    
    public String substring(int beginIndex) { return substring(beginIndex, value.length); }
    public String substring(int beginIndex, int endIndex) { return new String(value, beginIndex, endIndex - beginIndex); }
    
    public boolean equals(Object anObject) { 
        if (this == anObject) return true;
        if (anObject instanceof String) {
            String other = (String) anObject;
            if (value.length != other.value.length) return false;
            for (int i = 0; i < value.length; i++) if (value[i] != other.value[i]) return false;
            return true;
        }
        return false;
    }
    
    public int compareTo(String anotherString) { return toString().compareTo(anotherString.toString()); }
    public int hashCode() { return hash == 0 ? computeHash() : hash; }
    private int computeHash() { int h = 0; for (char c : value) h = 31 * h + c; return h; }
    public String toString() { return new String(value); }
    
    public static String valueOf(Object obj) { return obj == null ? "null" : obj.toString(); }
    public static String valueOf(char[] data) { return new String(data); }
    public static String valueOf(char[] data, int offset, int count) { return new String(data, offset, count); }
    public static String valueOf(boolean b) { return b ? "true" : "false"; }
    public static String valueOf(int i) { return Integer.toString(i); }
    public static String valueOf(long l) { return Long.toString(l); }
    public static String valueOf(float f) { return Float.toString(f); }
    public static String valueOf(double d) { return Double.toString(d); }
    
    public String concat(String str) { return this + str; }
    public boolean startsWith(String prefix) { return startsWith(prefix, 0); }
    public boolean startsWith(String prefix, int toffset) { return prefix.length() <= value.length - toffset && java.util.Arrays.equals(value, toffset, prefix.value, 0, prefix.length()); }
    public boolean endsWith(String suffix) { return startsWith(suffix, value.length - suffix.length()); }
    public int indexOf(int ch) { return indexOf(ch, 0); }
    public int indexOf(int ch, int fromIndex) { for (int i = fromIndex; i < value.length; i++) if (value[i] == ch) return i; return -1; }
    public int indexOf(String str) { return indexOf(str, 0); }
    public int indexOf(String str, int fromIndex) { return toString().indexOf(str.toString(), fromIndex); }
    public int lastIndexOf(int ch) { for (int i = value.length - 1; i >= 0; i--) if (value[i] == ch) return i; return -1; }
    public int lastIndexOf(String str) { return lastIndexOf(str, value.length); }
    public String replace(char oldChar, char newChar) { char[] newVal = value.clone(); for (int i = 0; i < newVal.length; i++) if (newVal[i] == oldChar) newVal[i] = newChar; return new String(newVal); }
    public String replace(CharSequence target, CharSequence replacement) { return toString().replace(target.toString(), replacement.toString()); }
    public String replaceAll(String regex, String replacement) { return toString().replaceAll(regex, replacement); }
    public String replaceFirst(String regex, String replacement) { return toString().replaceFirst(regex, replacement); }
    public String[] split(String regex) { return toString().split(regex); }
    public String[] split(String regex, int limit) { return toString().split(regex, limit); }
    public String toLowerCase() { return toString().toLowerCase(); }
    public String toUpperCase() { return toString().toUpperCase(); }
    public String trim() { return toString().trim(); }
    public boolean isEmpty() { return value.length == 0; }
    public static String format(String format, Object... args) { return java.lang.String.format(format, args); }
    public static String join(CharSequence delimiter, CharSequence... elements) { return java.lang.String.join(delimiter, elements); }
}
