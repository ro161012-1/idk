package sun.misc;

public final class Unsafe {
    private static final Unsafe theUnsafe = new Unsafe();
    
    public static Unsafe getUnsafe() { return theUnsafe; }
    
    public native int arrayBaseOffset(Class<?> arrayClass) /*-{ return 0; }-*/;
    public native int arrayIndexScale(Class<?> arrayClass) /*-{ return 1; }-*/;
    public native int addressSize() /*-{ return 4; }-*/;
    public native int pageSize() /*-{ return 4096; }-*/;
    
    public native Object allocateMemory(long bytes) /*-{ return null; }-*/;
    public native void freeMemory(Object address) /*-{ }-*/;
    public native long getByte(Object o, long offset) /*-{ return 0; }-*/;
    public native void putByte(Object o, long offset, long x) /*-{ }-*/;
    public native long getInt(Object o, long offset) /*-{ return 0; }-*/;
    public native void putInt(Object o, long offset, long x) /*-{ }-*/;
    public native long getLong(Object o, long offset) /*-{ return 0; }-*/;
    public native void putLong(Object o, long offset, long x) /*-{ }-*/;
    public native Object getObject(Object o, long offset) /*-{ return null; }-*/;
    public native void putObject(Object o, long offset, Object x) /*-{ }-*/;
    
    public native void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes) /*-{ }-*/;
    public native void setMemory(Object o, long offset, long bytes, long value) /*-{ }-*/;
    
    public native long allocateInstance(Class<?> cls) /*-{ return null; }-*/;
    public native void throwException(Throwable ee) /*-{ throw ee; }-*/;
    
    public native int getAndAddInt(Object o, long offset, int delta) /*-{ return 0; }-*/;
    public native int getAndAddLong(Object o, long offset, long delta) /*-{ return 0; }-*/;
    public native boolean compareAndSwapInt(Object o, long offset, int expected, int x) /*-{ return false; }-*/;
    public native boolean compareAndSwapLong(Object o, long offset, long expected, long x) /*-{ return false; }-*/;
    public native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x) /*-{ return false; }-*/;
    
    public native void park(boolean isAbsolute, long time) /*-{ }-*/;
    public native void unpark(Object thread) /*-{ }-*/;
    
    public native int objectFieldOffset(java.lang.reflect.Field f) /*-{ return 0; }-*/;
    public native int staticFieldOffset(java.lang.reflect.Field f) /*-{ return 0; }-*/;
    public native Object staticFieldBase(java.lang.reflect.Field f) /*-{ return null; }-*/;
    public native boolean shouldBeInitialized(Class<?> c) /*-{ return true; }-*/;
    public native void ensureClassInitialized(Class<?> c) /*-{ }-*/;
    
    public native int arrayBaseOffset(Class<?> arrayClass) /*-{ return 0; }-*/;
    public native int arrayIndexScale(Class<?> arrayClass) /*-{ return 1; }-*/;
}