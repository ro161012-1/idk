package java.util.regex;

public class Pattern implements Serializable {
    private final String pattern;
    private final int flags;
    
    public static final int UNIX_LINES = 1;
    public static final int CASE_INSENSITIVE = 2;
    public static final int COMMENTS = 4;
    public static final int MULTILINE = 8;
    public static final int LITERAL = 16;
    public static final int DOTALL = 32;
    public static final int UNICODE_CASE = 64;
    public static final int CANON_EQ = 128;
    
    private Pattern(String pattern, int flags) { this.pattern = pattern; this.flags = flags; }
    
    public static Pattern compile(String regex) { return new Pattern(regex, 0); }
    public static Pattern compile(String regex, int flags) { return new Pattern(regex, flags); }
    
    public Matcher matcher(CharSequence input) { return new Matcher(this, input); }
    public static boolean matches(String regex, CharSequence input) { return compile(regex).matcher(input).matches(); }
    public String[] split(CharSequence input) { return split(input, 0); }
    public String[] split(CharSequence input, int limit) {
        // Simplified split implementation
        String s = input.toString();
        if (limit == 0) return s.split(pattern);
        return s.split(pattern, limit);
    }
    public String pattern() { return pattern; }
    public int flags() { return flags; }
    
    @Override public String toString() { return pattern; }
}