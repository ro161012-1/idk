package java.net;

/**
 * GWT emulation of UnknownHostException.
 */
public class UnknownHostException extends IOException {
    public UnknownHostException() { super(); }
    public UnknownHostException(String message) { super(message); }
}