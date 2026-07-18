package java.lang;

public final class Character implements Serializable, Comparable<Character> {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 65535;
    public static final int SIZE = 16;
    public static final int BYTES = 2;
    public static final Class<Character> TYPE = char.class;
    
    private final char value;
    
    public Character(char value) { this.value = value; }
    
    public char charValue() { return value; }
    public static Character valueOf(char c) { return new Character(c); }
    
    public String toString() { return String.valueOf(value); }
    public int compareTo(Character another) { return value - another.value; }
    public boolean equals(Object obj) { return obj instanceof Character && value == ((Character)obj).value; }
    public int hashCode() { return value; }
    
    public static boolean isDigit(char ch) { return ch >= '0' && ch <= '9'; }
    public static boolean isLetter(char ch) { return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'); }
    public static boolean isLetterOrDigit(char ch) { return isLetter(ch) || isDigit(ch); }
    public static boolean isLowerCase(char ch) { return ch >= 'a' && ch <= 'z'; }
    public static boolean isUpperCase(char ch) { return ch >= 'A' && ch <= 'Z'; }
    public static char toLowerCase(char ch) { return isUpperCase(ch) ? (char)(ch + 32) : ch; }
    public static char toUpperCase(char ch) { return isLowerCase(ch) ? (char)(ch - 32) : ch; }
    public static int digit(char ch, int radix) { if (ch >= '0' && ch <= '9') return ch - '0'; if (ch >= 'a' && ch <= 'z') return ch - 'a' + 10; if (ch >= 'A' && ch <= 'Z') return ch - 'A' + 10; return -1; }
    public static char forDigit(int digit, int radix) { if (digit < 0 || digit >= radix) return 0; return digit < 10 ? (char)('0' + digit) : (char)('a' + digit - 10); }
}
