package java.util.regex;

public class Matcher {
    private final Pattern pattern;
    private final CharSequence input;
    private int start = 0;
    private int end = 0;
    private boolean matched = false;
    
    Matcher(Pattern pattern, CharSequence input) { this.pattern = pattern; this.input = input; }
    
    public int start() { if (!matched) throw new IllegalStateException("No match found"); return start; }
    public int start(int group) { if (!matched) throw new IllegalStateException("No match found"); return start; }
    public int end() { if (!matched) throw new IllegalStateException("No match found"); return end; }
    public int end(int group) { if (!matched) throw new IllegalStateException("No match found"); return end; }
    
    public String group() { return group(0); }
    public String group(int group) { if (!matched) return null; return input.subSequence(start, end).toString(); }
    public int groupCount() { return 0; }
    
    public boolean matches() {
        // Simplified - just check if entire input matches
        String s = input.toString();
        matched = s.matches(pattern.pattern());
        if (matched) { start = 0; end = s.length(); }
        return matched;
    }
    
    public boolean find() {
        // Simplified - find first match
        String s = input.toString();
        // For simplicity, just return matches() result
        if (!matched) {
            matched = s.matches(pattern.pattern());
            if (matched) { start = 0; end = s.length(); }
        }
        return matched;
    }
    
    public boolean find(int start) { return find(); }
    public boolean lookingAt() { return matches(); }
    public String replaceAll(String replacement) { return input.toString().replaceAll(pattern.pattern(), replacement); }
    public String replaceFirst(String replacement) { return input.toString().replaceFirst(pattern.pattern(), replacement); }
    public Matcher appendReplacement(StringBuffer sb, String replacement) { return this; }
    public StringBuffer appendTail(StringBuffer sb) { return sb; }
    public String toString() { return "Matcher[pattern=" + pattern + "]"; }
    
    public static String quoteReplacement(String s) { return s.replace("\\", "\\\\").replace("$", "\\$"); }
}