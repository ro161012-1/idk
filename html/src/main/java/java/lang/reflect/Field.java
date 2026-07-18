package java.lang.reflect;

/**
 * GWT emulation of Field - minimal implementation.
 */
public class Field extends AccessibleObject implements Member {
    private final Class<?> declaringClass;
    private final String name;
    private final Class<?> type;
    private final int modifiers;
    
    public Field(Class<?> declaringClass, String name, Class<?> type, int modifiers) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }
    
    public Class<?> getDeclaringClass() { return declaringClass; }
    public String getName() { return name; }
    public Class<?> getType() { return type; }
    public int getModifiers() { return modifiers; }
    public boolean isSynthetic() { return false; }
    public <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> annotationClass) { return null; }
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public Type getGenericType() { return type; }
    public String toString() { return modifiers + " " + type.getName() + " " + declaringClass.getName() + "." + name; }
    public String toGenericString() { return toString(); }
    public int hashCode() { return declaringClass.getName().hashCode() + name.hashCode(); }
    public boolean equals(Object obj) { return obj instanceof Field && ((Field)obj).name.equals(name); }
    
    public Object get(Object obj) throws IllegalAccessException {
        throw new UnsupportedOperationException("Reflection get not supported in GWT");
    }
    
    public void set(Object obj, Object value) throws IllegalAccessException {
        throw new UnsupportedOperationException("Reflection set not supported in GWT");
    }
    
    public boolean getBoolean(Object obj) { return false; }
    public byte getByte(Object obj) { return 0; }
    public char getChar(Object obj) { return 0; }
    public short getShort(Object obj) { return 0; }
    public int getInt(Object obj) { return 0; }
    public long getLong(Object obj) { return 0; }
    public float getFloat(Object obj) { return 0; }
    public double getDouble(Object obj) { return 0; }
    
    public void setBoolean(Object obj, boolean z) {}
    public void setByte(Object obj, byte b) {}
    public void setChar(Object obj, char c) {}
    public void setShort(Object obj, short s) {}
    public void setInt(Object obj, int i) {}
    public void setLong(Object obj, long l) {}
    public void setFloat(Object obj, float f) {}
    public void setDouble(Object obj, double d) {}
}