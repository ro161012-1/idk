package java.lang.reflect;

/**
 * GWT emulation of ReflectiveOperationException.
 */
public class ReflectiveOperationException extends Exception {
    public ReflectiveOperationException() { super(); }
    public ReflectiveOperationException(String message) { super(message); }
    public ReflectiveOperationException(String message, Throwable cause) { super(message, cause); }
    public ReflectiveOperationException(Throwable cause) { super(cause); }
}