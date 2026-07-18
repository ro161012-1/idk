package java.lang.reflect;

/**
 * GWT emulation of IllegalArgumentException (for reflection package).
 */
public class IllegalArgumentException extends java.lang.IllegalArgumentException {
    public IllegalArgumentException() { super(); }
    public IllegalArgumentException(String message) { super(message); }
    public IllegalArgumentException(String message, Throwable cause) { super(message, cause); }
    public IllegalArgumentException(Throwable cause) { super(cause); }
}