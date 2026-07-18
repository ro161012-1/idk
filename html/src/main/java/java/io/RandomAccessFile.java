package java.io;

/**
 * GWT emulation of RandomAccessFile - uses IndexedDB.
 */
public class RandomAccessFile implements Closeable, DataOutput, DataInput {
    
    private final String path;
    private final String mode;
    private long pointer = 0;
    private byte[] data;
    
    public RandomAccessFile(String name, String mode) throws FileNotFoundException {
        this.path = name;
        this.mode = mode;
        this.data = GwtFileSystem.readFile(name);
        if (data == null && mode.contains("w")) {
            data = new byte[0];
        } else if (data == null) {
            throw new FileNotFoundException(name);
        }
    }
    
    public RandomAccessFile(File file, String mode) throws FileNotFoundException {
        this(file.getPath(), mode);
    }
    
    @Override public void close() throws IOException {
        if (mode.contains("w") || mode.contains("rw")) {
            GwtFileSystem.writeFile(path, data);
        }
    }
    
    @Override public void write(int b) throws IOException {
        ensureCapacity(pointer + 1);
        data[(int)pointer++] = (byte) b;
    }
    
    @Override public void write(byte[] b) throws IOException { write(b, 0, b.length); }
    
    @Override public void write(byte[] b, int off, int len) throws IOException {
        ensureCapacity(pointer + len);
        System.arraycopy(b, off, data, (int)pointer, len);
        pointer += len;
    }
    
    @Override public void writeBoolean(boolean v) throws IOException { write(v ? 1 : 0); }
    @Override public void writeByte(int v) throws IOException { write(v); }
    @Override public void writeShort(int v) throws IOException { write((v >>> 8) & 0xff); write(v & 0xff); }
    @Override public void writeChar(int v) throws IOException { writeShort(v); }
    @Override public void writeInt(int v) throws IOException {
        write((v >>> 24) & 0xff);
        write((v >>> 16) & 0xff);
        write((v >>> 8) & 0xff);
        write(v & 0xff);
    }
    @Override public void writeLong(long v) throws IOException {
        write((int)(v >>> 56) & 0xff);
        write((int)(v >>> 48) & 0xff);
        write((int)(v >>> 40) & 0xff);
        write((int)(v >>> 32) & 0xff);
        write((int)(v >>> 24) & 0xff);
        write((int)(v >>> 16) & 0xff);
        write((int)(v >>> 8) & 0xff);
        write((int)v & 0xff);
    }
    @Override public void writeFloat(float v) throws IOException { writeInt(Float.floatToIntBits(v)); }
    @Override public void writeDouble(double v) throws IOException { writeLong(Double.doubleToLongBits(v)); }
    @Override public void writeBytes(String s) throws IOException { for (int i = 0; i < s.length(); i++) write((byte)s.charAt(i)); }
    @Override public void writeChars(String s) throws IOException { for (int i = 0; i < s.length(); i++) writeChar(s.charAt(i)); }
    @Override public void writeUTF(String s) throws IOException { throw new UnsupportedOperationException(); }
    
    @Override public int read() throws IOException {
        if (pointer >= data.length) return -1;
        return data[(int)pointer++] & 0xff;
    }
    
    @Override public int read(byte[] b) throws IOException { return read(b, 0, b.length); }
    
    @Override public int read(byte[] b, int off, int len) throws IOException {
        if (pointer >= data.length) return -1;
        int avail = (int)(data.length - pointer);
        if (len > avail) len = avail;
        System.arraycopy(data, (int)pointer, b, off, len);
        pointer += len;
        return len;
    }
    
    @Override public void readFully(byte[] b) throws IOException { readFully(b, 0, b.length); }
    
    @Override public void readFully(byte[] b, int off, int len) throws IOException {
        if (pointer + len > data.length) throw new EOFException();
        System.arraycopy(data, (int)pointer, b, off, len);
        pointer += len;
    }
    
    @Override public int skipBytes(int n) throws IOException {
        long newPos = pointer + n;
        if (newPos > data.length) newPos = data.length;
        int skipped = (int)(newPos - pointer);
        pointer = newPos;
        return skipped;
    }
    
    @Override public boolean readBoolean() throws IOException { return read() != 0; }
    @Override public byte readByte() throws IOException { return (byte)read(); }
    @Override public int readUnsignedByte() throws IOException { return read(); }
    @Override public short readShort() throws IOException { return (short)((read() << 8) | read()); }
    @Override public int readUnsignedShort() throws IOException { return (read() << 8) | read(); }
    @Override public char readChar() throws IOException { return (char)readShort(); }
    @Override public int readInt() throws IOException { return (read() << 24) | (read() << 16) | (read() << 8) | read(); }
    @Override public long readLong() throws IOException {
        return ((long)read() << 56) | ((long)read() << 48) | ((long)read() << 40) | ((long)read() << 32) |
               ((long)read() << 24) | ((long)read() << 16) | ((long)read() << 8) | (long)read();
    }
    @Override public float readFloat() throws IOException { return Float.intBitsToFloat(readInt()); }
    @Override public double readDouble() throws IOException { return Double.longBitsToDouble(readLong()); }
    @Override public String readLine() throws IOException { return null; }
    @Override public String readUTF() throws IOException { throw new UnsupportedOperationException(); }
    
    public long getFilePointer() { return pointer; }
    public void seek(long pos) throws IOException { if (pos < 0) throw new IOException(); pointer = pos; }
    public long length() { return data.length; }
    public void setLength(long newLength) throws IOException { ensureCapacity((int)newLength); }
    
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            byte[] newData = new byte[Math.max(minCapacity, data.length * 2)];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }
    
    private static class GwtFileSystem {
        private static native byte[] readFile(String path) /*-{ return null; }-*/;
        private static native void writeFile(String path, byte[] data) /*-{ }-*/;
    }
}