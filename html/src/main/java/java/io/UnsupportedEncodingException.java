package java.io;

/**
 * GWT emulation of UnsupportedEncodingException.
 */
public class UnsupportedEncodingException extends IOException {
    public UnsupportedEncodingException() { super(); }
    public UnsupportedEncodingException(String message) { super(message); }
}