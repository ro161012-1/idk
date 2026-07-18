package java.lang;

public class StackTraceElement implements Serializable {
    private final String className;
    private final String methodName;
    private final String fileName;
    private final int lineNumber;
    
    public StackTraceElement(String className, String methodName, String fileName, int lineNumber) {
        this.className = className;
        this.methodName = methodName;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }
    
    public String getClassName() { return className; }
    public String getMethodName() { return methodName; }
    public String getFileName() { return fileName; }
    public int getLineNumber() { return lineNumber; }
    public boolean isNativeMethod() { return false; }
    
    public String toString() { return className + "." + methodName + (fileName != null ? "(" + fileName + ":" + lineNumber + ")" : "(Unknown Source)"); }
    public boolean equals(Object obj) { return obj instanceof StackTraceElement && toString().equals(obj.toString()); }
    public int hashCode() { return toString().hashCode(); }
}
