package java.lang.reflect;

/**
 * GWT emulation of Array - minimal implementation.
 */
public class Array {
    
    public static Object newInstance(Class<?> componentType, int length) {
        if (componentType == int.class || componentType == Integer.class) return new int[length];
        if (componentType == byte.class || componentType == Byte.class) return new byte[length];
        if (componentType == short.class || componentType == Short.class) return new short[length];
        if (componentType == long.class || componentType == Long.class) return new long[length];
        if (componentType == float.class || componentType == Float.class) return new float[length];
        if (componentType == double.class || componentType == Double.class) return new double[length];
        if (componentType == boolean.class || componentType == Boolean.class) return new boolean[length];
        if (componentType == char.class || componentType == Character.class) return new char[length];
        return java.lang.reflect.Array.newInstance(componentType, length); // fallback
    }
    
    public static Object newInstance(Class<?> componentType, int... lengths) {
        return newInstance(componentType, lengths.length > 0 ? lengths[0] : 0);
    }
    
    public static int getLength(Object array) {
        if (array instanceof int[]) return ((int[])array).length;
        if (array instanceof byte[]) return ((byte[])array).length;
        if (array instanceof short[]) return ((short[])array).length;
        if (array instanceof long[]) return ((long[])array).length;
        if (array instanceof float[]) return ((float[])array).length;
        if (array instanceof double[]) return ((double[])array).length;
        if (array instanceof boolean[]) return ((boolean[])array).length;
        if (array instanceof char[]) return ((char[])array).length;
        if (array instanceof Object[]) return ((Object[])array).length;
        return 0;
    }
    
    public static Object get(Object array, int index) {
        if (array instanceof int[]) return ((int[])array)[index];
        if (array instanceof byte[]) return ((byte[])array)[index];
        if (array instanceof short[]) return ((short[])array)[index];
        if (array instanceof long[]) return ((long[])array)[index];
        if (array instanceof float[]) return ((float[])array)[index];
        if (array instanceof double[]) return ((double[])array)[index];
        if (array instanceof boolean[]) return ((boolean[])array)[index];
        if (array instanceof char[]) return ((char[])array)[index];
        if (array instanceof Object[]) return ((Object[])array)[index];
        return null;
    }
    
    public static void set(Object array, int index, Object value) {
        if (array instanceof int[]) ((int[])array)[index] = (Integer)value;
        else if (array instanceof byte[]) ((byte[])array)[index] = (Byte)value;
        else if (array instanceof short[]) ((short[])array)[index] = (Short)value;
        else if (array instanceof long[]) ((long[])array)[index] = (Long)value;
        else if (array instanceof float[]) ((float[])array)[index] = (Float)value;
        else if (array instanceof double[]) ((double[])array)[index] = (Double)value;
        else if (array instanceof boolean[]) ((boolean[])array)[index] = (Boolean)value;
        else if (array instanceof char[]) ((char[])array)[index] = (Character)value;
        else if (array instanceof Object[]) ((Object[])array)[index] = value;
    }
}