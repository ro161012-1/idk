package java.util;

public class StringJoiner {
    private final String delimiter;
    private final String prefix;
    private final String suffix;
    private StringBuilder value;
    private boolean empty = true;
    
    public StringJoiner(CharSequence delimiter) { this(delimiter, "", ""); }
    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        this.delimiter = delimiter.toString();
        this.prefix = prefix.toString();
        this.suffix = suffix.toString();
        this.value = new StringBuilder().append(this.prefix);
    }
    
    public StringJoiner add(CharSequence newElement) {
        if (!empty) value.append(delimiter);
        value.append(newElement);
        empty = false;
        return this;
    }
    
    public StringJoiner merge(StringJoiner other) {
        if (other != null && !other.empty) {
            if (!empty) value.append(delimiter);
            value.append(other.value.substring(other.prefix.length()));
            empty = false;
        }
        return this;
    }
    
    public int length() { return value.length(); }
    public String toString() { return value.append(suffix).toString(); }
}