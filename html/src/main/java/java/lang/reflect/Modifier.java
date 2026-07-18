package java.lang.reflect;

/**
 * GWT emulation of Modifier.
 */
public class Modifier {
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int TRANSIENT = 128;
    public static final int NATIVE = 256;
    public static final int INTERFACE = 512;
    public static final int ABSTRACT = 1024;
    public static final int STRICT = 2048;
    
    public static boolean isPublic(int mod) { return (mod & PUBLIC) != 0; }
    public static boolean isPrivate(int mod) { return (mod & PRIVATE) != 0; }
    public static boolean isProtected(int mod) { return (mod & PROTECTED) != 0; }
    public static boolean isStatic(int mod) { return (mod & STATIC) != 0; }
    public static boolean isFinal(int mod) { return (mod & FINAL) != 0; }
    public static boolean isSynchronized(int mod) { return (mod & SYNCHRONIZED) != 0; }
    public static boolean isVolatile(int mod) { return (mod & VOLATILE) != 0; }
    public static boolean isTransient(int mod) { return (mod & TRANSIENT) != 0; }
    public static boolean isNative(int mod) { return (mod & NATIVE) != 0; }
    public static boolean isInterface(int mod) { return (mod & INTERFACE) != 0; }
    public static boolean isAbstract(int mod) { return (mod & ABSTRACT) != 0; }
    public static boolean isStrict(int mod) { return (mod & STRICT) != 0; }
    
    public static String toString(int mod) {
        StringBuilder sb = new StringBuilder();
        if (isPublic(mod)) sb.append("public ");
        if (isProtected(mod)) sb.append("protected ");
        if (isPrivate(mod)) sb.append("private ");
        if (isAbstract(mod)) sb.append("abstract ");
        if (isStatic(mod)) sb.append("static ");
        if (isFinal(mod)) sb.append("final ");
        if (isTransient(mod)) sb.append("transient ");
        if (isVolatile(mod)) sb.append("volatile ");
        if (isSynchronized(mod)) sb.append("synchronized ");
        if (isNative(mod)) sb.append("native ");
        if (isStrict(mod)) sb.append("strictfp ");
        if (isInterface(mod)) sb.append("interface ");
        return sb.toString().trim();
    }
}