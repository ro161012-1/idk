package java.lang;

public class Throwable implements Serializable {
    private String detailMessage;
    private Throwable cause;
    private StackTraceElement[] stackTrace;
    private static final long serialVersionUID = 1L;
    
    public Throwable() { fillInStackTrace(); }
    public Throwable(String message) { this.detailMessage = message; fillInStackTrace(); }
    public Throwable(String message, Throwable cause) { this.detailMessage = message; this.cause = cause; fillInStackTrace(); }
    public Throwable(Throwable cause) { this.detailMessage = cause == null ? null : cause.toString(); this.cause = cause; fillInStackTrace(); }
    
    public String getMessage() { return detailMessage; }
    public String getLocalizedMessage() { return getMessage(); }
    public Throwable getCause() { return cause; }
    public Throwable initCause(Throwable cause) { this.cause = cause; return this; }
    
    public StackTraceElement[] getStackTrace() { return stackTrace; }
    public void setStackTrace(StackTraceElement[] stackTrace) { this.stackTrace = stackTrace; }
    
    public void printStackTrace() { printStackTrace(System.err); }
    public void printStackTrace(PrintStream s) { printStackTrace(new java.io.PrintWriter(s)); }
    public void printStackTrace(java.io.PrintWriter s) {
        s.println(toString());
        if (stackTrace != null) for (StackTraceElement e : stackTrace) s.println("\tat " + e);
        if (cause != null) { s.println("Caused by: "); cause.printStackTrace(s); }
    }
    
    public String toString() { return getClass().getName() + (detailMessage != null ? ": " + detailMessage : ""); }
    
    public synchronized Throwable fillInStackTrace() {
        // In GWT, we can't get real stack trace easily
        this.stackTrace = new StackTraceElement[0];
        return this;
    }
}
