package java.util;

public class StringTokenizer implements Enumeration<String> {
    private String str;
    private String delim = " \t\n\r\f";
    private int pos = 0;
    private int maxPos;
    private boolean retDelims = false;
    
    public StringTokenizer(String str) { this.str = str; maxPos = str.length(); }
    public StringTokenizer(String str, String delim) { this.str = str; this.delim = delim; maxPos = str.length(); }
    public StringTokenizer(String str, String delim, boolean returnDelims) { this(str, delim); this.retDelims = returnDelims; }
    
    public boolean hasMoreTokens() { return nextTokenIndex() >= 0; }
    public String nextToken() { return nextToken(delim); }
    public String nextToken(String delim) {
        this.delim = delim;
        int start = nextTokenIndex();
        if (start < 0) throw new NoSuchElementException();
        int end = start;
        while (end < maxPos && !isDelimiter(str.charAt(end))) end++;
        if (retDelims && start == end) end++;
        return str.substring(start, end);
    }
    
    public int countTokens() { int count = 0; int p = pos; while (p < maxPos) { if (!isDelimiter(str.charAt(p))) { count++; while (p < maxPos && !isDelimiter(str.charAt(p))) p++; } else p++; } return count; }
    
    private int nextTokenIndex() {
        int p = pos;
        if (!retDelims) { while (p < maxPos && isDelimiter(str.charAt(p))) p++; }
        else { if (p >= maxPos) return -1; }
        return p < maxPos ? p : -1;
    }
    
    private boolean isDelimiter(char c) { return delim.indexOf(c) >= 0; }
    
    @Override public boolean hasMoreElements() { return hasMoreTokens(); }
    @Override public String nextElement() { return nextToken(); }
}