package java.lang.reflect;

/**
 * GWT emulation of Executable - base class for Method and Constructor.
 */
public abstract class Executable extends AccessibleObject implements Member {
    public abstract Class<?> getDeclaringClass();
    public abstract Class<?>[] getParameterTypes();
    public abstract int getModifiers();
    public abstract boolean isVarArgs();
    public abstract boolean isSynthetic();
    public abstract <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> annotationClass);
    public abstract java.lang.annotation.Annotation[] getAnnotations();
    public abstract java.lang.annotation.Annotation[] getDeclaredAnnotations();
    public abstract java.lang.annotation.Annotation[][] getParameterAnnotations();
    public abstract Type[] getGenericParameterTypes();
    public abstract Class<?>[] getExceptionTypes();
    public abstract Type[] getGenericExceptionTypes();
    public abstract String toString();
    public abstract String toGenericString();
    public abstract int hashCode();
    public abstract boolean equals(Object obj);
}