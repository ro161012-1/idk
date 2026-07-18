package java.io;

/**
 * GWT emulation of EOFException.
 */
public class EOFException extends IOException {
    public EOFException() { super(); }
    public EOFException(String message) { super(message); }
}