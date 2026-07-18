package java.lang.reflect;

/**
 * GWT emulation of InstantiationException.
 */
public class InstantiationException extends ReflectiveOperationException {
    public InstantiationException() { super(); }
    public InstantiationException(String message) { super(message); }
}