package java.lang.reflect;

/**
 * GWT emulation of Member interface.
 */
public interface Member {
    public static final int DECLARED = 1;
    public static final int PUBLIC = 2;
    
    Class<?> getDeclaringClass();
    String getName();
    int getModifiers();
    boolean isSynthetic();
}