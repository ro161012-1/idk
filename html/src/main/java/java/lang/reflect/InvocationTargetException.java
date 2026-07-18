package java.lang.reflect;

/**
 * GWT emulation of InvocationTargetException.
 */
public class InvocationTargetException extends ReflectiveOperationException {
    private final Throwable target;
    
    public InvocationTargetException(Throwable target) {
        this.target = target;
    }
    
    public InvocationTargetException(Throwable target, String s) {
        super(s);
        this.target = target;
    }
    
    public Throwable getTargetException() { return target; }
    public Throwable getCause() { return target; }
}