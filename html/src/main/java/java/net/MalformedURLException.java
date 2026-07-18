package java.net;

/**
 * GWT emulation of MalformedURLException.
 */
public class MalformedURLException extends IOException {
    public MalformedURLException() { super(); }
    public MalformedURLException(String message) { super(message); }
}