package java.lang.reflect;

/**
 * GWT emulation of AccessibleObject.
 */
public class AccessibleObject {
    public void setAccessible(boolean flag) {}
    public boolean isAccessible() { return true; }
    public static void setAccessible(AccessibleObject[] array, boolean flag) {}
    
    public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> annotationClass) { return null; }
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
}