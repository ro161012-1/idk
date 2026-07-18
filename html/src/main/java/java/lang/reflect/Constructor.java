package java.lang.reflect;

/**
 * GWT emulation of Constructor - minimal implementation.
 */
public class Constructor<T> extends Executable {
    private final Class<T> declaringClass;
    private final Class<?>[] parameterTypes;
    private final int modifiers;
    
    public Constructor(Class<T> declaringClass, Class<?>[] parameterTypes, int modifiers) {
        this.declaringClass = declaringClass;
        this.parameterTypes = parameterTypes;
        this.modifiers = modifiers;
    }
    
    public Class<T> getDeclaringClass() { return declaringClass; }
    public Class<?>[] getParameterTypes() { return parameterTypes; }
    public int getModifiers() { return modifiers; }
    public boolean isVarArgs() { return false; }
    public boolean isSynthetic() { return false; }
    public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> annotationClass) { return null; }
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[][] getParameterAnnotations() { return new java.lang.annotation.Annotation[0][]; }
    public Type[] getGenericParameterTypes() { return new Type[0]; }
    public Class<?>[] getExceptionTypes() { return new Class<?>[0]; }
    public Type[] getGenericExceptionTypes() { return new Type[0]; }
    public String toString() { return modifiers + " " + declaringClass.getName(); }
    public String toGenericString() { return toString(); }
    public int hashCode() { return declaringClass.getName().hashCode(); }
    public boolean equals(Object obj) { return obj instanceof Constructor; }
    
    public T newInstance(Object... initargs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            return declaringClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new InstantiationException(e.getMessage());
        }
    }
    
    public void setAccessible(boolean flag) {}
    public boolean isAccessible() { return true; }
}