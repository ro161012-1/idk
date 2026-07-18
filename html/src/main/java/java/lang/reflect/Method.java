package java.lang.reflect;

/**
 * GWT emulation of Method - minimal implementation.
 */
public class Method extends Executable {
    private final Class<?> declaringClass;
    private final String name;
    private final Class<?> returnType;
    private final Class<?>[] parameterTypes;
    private final int modifiers;
    
    public Method(Class<?> declaringClass, String name, Class<?> returnType, Class<?>[] parameterTypes, int modifiers) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.modifiers = modifiers;
    }
    
    public Class<?> getDeclaringClass() { return declaringClass; }
    public String getName() { return name; }
    public Class<?> getReturnType() { return returnType; }
    public Class<?>[] getParameterTypes() { return parameterTypes; }
    public int getModifiers() { return modifiers; }
    public boolean isBridge() { return false; }
    public boolean isVarArgs() { return false; }
    public boolean isSynthetic() { return false; }
    public <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> annotationClass) { return null; }
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[][] getParameterAnnotations() { return new java.lang.annotation.Annotation[0][]; }
    public Type[] getGenericParameterTypes() { return new Type[0]; }
    public Type getGenericReturnType() { return returnType; }
    public Class<?>[] getExceptionTypes() { return new Class<?>[0]; }
    public Type[] getGenericExceptionTypes() { return new Type[0]; }
    public String toString() { return modifiers + " " + returnType.getName() + " " + declaringClass.getName() + "." + name; }
    public String toGenericString() { return toString(); }
    public int hashCode() { return declaringClass.getName().hashCode() + name.hashCode(); }
    public boolean equals(Object obj) { return obj instanceof Method && ((Method)obj).name.equals(name); }
    
    public Object invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        throw new UnsupportedOperationException("Reflection invoke not supported in GWT");
    }
    
    public void setAccessible(boolean flag) {}
    public boolean isAccessible() { return true; }
}