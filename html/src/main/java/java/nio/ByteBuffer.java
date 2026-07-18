package java.nio;

/**
 * GWT emulation of ByteBuffer - minimal implementation.
 */
public abstract class ByteBuffer extends Buffer implements Comparable<ByteBuffer> {
    
    public static ByteBuffer allocate(int capacity) {
        return new HeapByteBuffer(capacity);
    }
    
    public static ByteBuffer allocateDirect(int capacity) {
        return allocate(capacity); // No direct buffers in GWT
    }
    
    public static ByteBuffer wrap(byte[] array) {
        return wrap(array, 0, array.length);
    }
    
    public static ByteBuffer wrap(byte[] array, int offset, int length) {
        HeapByteBuffer buf = new HeapByteBuffer(array.length);
        buf.array = array;
        buf.position = offset;
        buf.limit = offset + length;
        return buf;
    }
    
    public abstract byte get();
    public abstract byte get(int index);
    public abstract ByteBuffer put(byte b);
    public abstract ByteBuffer put(int index, byte b);
    public abstract ByteBuffer put(byte[] src, int offset, int length);
    public abstract boolean hasArray();
    public abstract byte[] array();
    public abstract int arrayOffset();
    public abstract ByteBuffer compact();
    public abstract boolean isDirect();
    public abstract ByteOrder order();
    public abstract ByteBuffer order(ByteOrder bo);
    
    // Primitive get/put
    public abstract char getChar();
    public abstract char getChar(int index);
    public abstract ByteBuffer putChar(char value);
    public abstract ByteBuffer putChar(int index, char value);
    
    public abstract short getShort();
    public abstract short getShort(int index);
    public abstract ByteBuffer putShort(short value);
    public abstract ByteBuffer putShort(int index, short value);
    
    public abstract int getInt();
    public abstract int getInt(int index);
    public abstract ByteBuffer putInt(int value);
    public abstract ByteBuffer putInt(int index, int value);
    
    public abstract long getLong();
    public abstract long getLong(int index);
    public abstract ByteBuffer putLong(long value);
    public abstract ByteBuffer putLong(int index, long value);
    
    public abstract float getFloat();
    public abstract float getFloat(int index);
    public abstract ByteBuffer putFloat(float value);
    public abstract ByteBuffer putFloat(int index, float value);
    
    public abstract double getDouble();
    public abstract double getDouble(int index);
    public abstract ByteBuffer putDouble(double value);
    public abstract ByteBuffer putDouble(int index, double value);
    
    public final String toString() { return "ByteBuffer[pos=" + position() + ", lim=" + limit() + ", cap=" + capacity() + "]"; }
    public final int hashCode() { return super.hashCode(); }
    public final boolean equals(Object ob) { return super.equals(ob); }
    public final int compareTo(ByteBuffer that) { return 0; }
    
    private static class HeapByteBuffer extends ByteBuffer {
        byte[] array;
        int position = 0;
        int limit = 0;
        int capacity = 0;
        int mark = -1;
        
        HeapByteBuffer(int cap) {
            this.array = new byte[cap];
            this.capacity = cap;
            this.limit = cap;
        }
        
        HeapByteBuffer(byte[] array) {
            this.array = array;
            this.capacity = array.length;
            this.limit = array.length;
        }
        
        @Override public ByteBuffer slice() { return null; }
        @Override public ByteBuffer duplicate() { return null; }
        @Override public ByteBuffer asReadOnlyBuffer() { return null; }
        @Override public byte get() { return array[position++]; }
        @Override public byte get(int index) { return array[index]; }
        @Override public ByteBuffer put(byte b) { array[position++] = b; return this; }
        @Override public ByteBuffer put(int index, byte b) { array[index] = b; return this; }
        @Override public ByteBuffer put(byte[] src, int offset, int length) { 
            System.arraycopy(src, offset, array, position, length);
            position += length;
            return this;
        }
        @Override public boolean hasArray() { return true; }
        @Override public byte[] array() { return array; }
        @Override public int arrayOffset() { return 0; }
        @Override public ByteBuffer compact() { return this; }
        @Override public boolean isDirect() { return false; }
        @Override public ByteOrder order() { return ByteOrder.BIG_ENDIAN; }
        @Override public ByteBuffer order(ByteOrder bo) { return this; }
        
        @Override public char getChar() { return 0; }
        @Override public char getChar(int index) { return 0; }
        @Override public ByteBuffer putChar(char value) { return this; }
        @Override public ByteBuffer putChar(int index, char value) { return this; }
        @Override public short getShort() { return 0; }
        @Override public short getShort(int index) { return 0; }
        @Override public ByteBuffer putShort(short value) { return this; }
        @Override public ByteBuffer putShort(int index, short value) { return this; }
        @Override public int getInt() { return 0; }
        @Override public int getInt(int index) { return 0; }
        @Override public ByteBuffer putInt(int value) { return this; }
        @Override public ByteBuffer putInt(int index, int value) { return this; }
        @Override public long getLong() { return 0; }
        @Override public long getLong(int index) { return 0; }
        @Override public ByteBuffer putLong(long value) { return this; }
        @Override public ByteBuffer putLong(int index, long value) { return this; }
        @Override public float getFloat() { return 0; }
        @Override public float getFloat(int index) { return 0; }
        @Override public ByteBuffer putFloat(float value) { return this; }
        @Override public ByteBuffer putFloat(int index, float value) { return this; }
        @Override public double getDouble() { return 0; }
        @Override public double getDouble(int index) { return 0; }
        @Override public ByteBuffer putDouble(double value) { return this; }
        @Override public ByteBuffer putDouble(int index, double value) { return this; }
    }
}