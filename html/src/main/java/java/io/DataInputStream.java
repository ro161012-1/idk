package java.io;

/**
 * GWT emulation of DataInputStream.
 */
public class DataInputStream extends InputStream implements DataInput {
    
    private final InputStream in;
    private final byte[] buffer = new byte[8];
    
    public DataInputStream(InputStream in) { this.in = in; }
    
    @Override public void close() throws IOException { in.close(); }
    @Override public int read() throws IOException { return in.read(); }
    @Override public int read(byte[] b) throws IOException { return in.read(b); }
    @Override public int read(byte[] b, int off, int len) throws IOException { return in.read(b, off, len); }
    @Override public long skip(long n) throws IOException { return in.skip(n); }
    @Override public int available() throws IOException { return in.available(); }
    @Override public void mark(int readlimit) { in.mark(readlimit); }
    @Override public void reset() throws IOException { in.reset(); }
    @Override public boolean markSupported() { return in.markSupported(); }
    
    @Override public final void readFully(byte[] b) throws IOException { readFully(b, 0, b.length); }
    @Override public final void readFully(byte[] b, int off, int len) throws IOException {
        if (len < 0) throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = in.read(b, off + n, len - n);
            if (count < 0) throw new EOFException();
            n += count;
        }
    }
    @Override public final int skipBytes(int n) throws IOException {
        int total = 0;
        while (total < n) {
            int count = in.read(buffer, 0, Math.min(n - total, buffer.length));
            if (count < 0) break;
            total += count;
        }
        return total;
    }
    
    @Override public final boolean readBoolean() throws IOException { return read() != 0; }
    @Override public final byte readByte() throws IOException { int b = read(); if (b < 0) throw new EOFException(); return (byte) b; }
    @Override public final int readUnsignedByte() throws IOException { int b = read(); if (b < 0) throw new EOFException(); return b; }
    @Override public final short readShort() throws IOException { readFully(buffer, 0, 2); return (short)((buffer[0] << 8) | (buffer[1] & 0xff)); }
    @Override public final int readUnsignedShort() throws IOException { readFully(buffer, 0, 2); return ((buffer[0] & 0xff) << 8) | (buffer[1] & 0xff); }
    @Override public final char readChar() throws IOException { readFully(buffer, 0, 2); return (char)((buffer[0] << 8) | (buffer[1] & 0xff)); }
    @Override public final int readInt() throws IOException { readFully(buffer, 0, 4); return (buffer[0] << 24) | ((buffer[1] & 0xff) << 16) | ((buffer[2] & 0xff) << 8) | (buffer[3] & 0xff); }
    @Override public final long readLong() throws IOException { readFully(buffer, 0, 8); return ((long)buffer[0] << 56) | ((long)(buffer[1] & 0xff) << 48) | ((long)(buffer[2] & 0xff) << 40) | ((long)(buffer[3] & 0xff) << 32) | ((long)(buffer[4] & 0xff) << 24) | ((long)(buffer[5] & 0xff) << 16) | ((long)(buffer[6] & 0xff) << 8) | (long)(buffer[7] & 0xff); }
    @Override public final float readFloat() throws IOException { return Float.intBitsToFloat(readInt()); }
    @Override public final double readDouble() throws IOException { return Double.longBitsToDouble(readLong()); }
    
    @Override public final String readLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = read()) != -1) {
            if (c == '\n') break;
            if (c == '\r') {
                if (in.markSupported()) {
                    in.mark(1);
                    if (read() != '\n') in.reset();
                }
                break;
            }
            sb.append((char) c);
        }
        if (c == -1 && sb.length() == 0) return null;
        return sb.toString();
    }
    
    @Override public final String readUTF() throws IOException {
        int utflen = readUnsignedShort();
        byte[] bytes = new byte[utflen];
        readFully(bytes);
        return decodeUTF(bytes);
    }
    
    private String decodeUTF(byte[] bytes) {
        // Simplified UTF-8 decode
        return new String(bytes);
    }
}