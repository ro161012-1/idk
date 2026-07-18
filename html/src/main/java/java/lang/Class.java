package java.lang;

public final class Class<T> implements Serializable {
    private final String name;
    
    private Class(String name) { this.name = name; }
    
    public static Class<?> forName(String className) throws ClassNotFoundException {
        // In GWT, we can't dynamically load classes
        throw new ClassNotFoundException(className);
    }
    
    public String getName() { return name; }
    public String getSimpleName() { int idx = name.lastIndexOf('.'); return idx >= 0 ? name.substring(idx + 1) : name; }
    public String getCanonicalName() { return name; }
    public boolean isPrimitive() { return false; }
    public boolean isInterface() { return false; }
    public boolean isArray() { return false; }
    public boolean isAnnotation() { return false; }
    public boolean isSynthetic() { return false; }
    public boolean isEnum() { return false; }
    public boolean isAnonymousClass() { return false; }
    public boolean isLocalClass() { return false; }
    public boolean isMemberClass() { return false; }
    
    public int getModifiers() { return 0; }
    public Class<? super T> getSuperclass() { return null; }
    public Class<?>[] getInterfaces() { return new Class<?>[0]; }
    public Class<?> getDeclaringClass() { return null; }
    public Class<?> getEnclosingClass() { return null; }
    public Package getPackage() { return null; }
    
    public Constructor<T> getConstructor(Class<?>... parameterTypes) { return null; }
    public Constructor<?>[] getConstructors() { return new Constructor<?>[0]; }
    public Constructor<?>[] getDeclaredConstructors() { return new Constructor<?>[0]; }
    public Method getMethod(String name, Class<?>... parameterTypes) { return null; }
    public Method[] getMethods() { return new Method[0]; }
    public Method[] getDeclaredMethods() { return new Method[0]; }
    public Field getField(String name) { return null; }
    public Field[] getFields() { return new Field[0]; }
    public Field[] getDeclaredFields() { return new Field[0]; }
    
    public T newInstance() throws InstantiationException, IllegalAccessException { return null; }
    public boolean isInstance(Object obj) { return false; }
    public boolean isAssignableFrom(Class<?> cls) { return false; }
    public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> annotationClass) { return null; }
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
    
    public String toString() { return "class " + name; }
    public String toGenericString() { return toString(); }
    
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getPrimitiveClass(String name) {
        if ("int".equals(name)) return (Class<T>)Integer.TYPE;
        if ("long".equals(name)) return (Class<T>)Long.TYPE;
        if ("float".equals(name)) return (Class<T>)Float.TYPE;
        if ("double".equals(name)) return (Class<T>)Double.TYPE;
        if ("boolean".equals(name)) return (Class<T>)Boolean.TYPE;
        if ("char".equals(name)) return (Class<T>)Character.TYPE;
        if ("byte".equals(name)) return (Class<T>)Byte.TYPE;
        if ("short".equals(name)) return (Class<T>)Short.TYPE;
        if ("void".equals(name)) return (Class<T>)Void.TYPE;
        return null;
    }
}
