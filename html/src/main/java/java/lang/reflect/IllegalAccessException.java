package java.lang.reflect;

/**
 * GWT emulation of IllegalAccessException.
 */
public class IllegalAccessException extends ReflectiveOperationException {
    public IllegalAccessException() { super(); }
    public IllegalAccessException(String message) { super(message); }
}