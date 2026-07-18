package java.io;

/**
 * GWT emulation of InputStream.
 */
public abstract class InputStream implements Closeable {
    
    public abstract int read() throws IOException;
    
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }
    
    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) throw new NullPointerException();
        if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException();
        if (len == 0) return 0;
        
        int c = read();
        if (c == -1) return -1;
        b[off] = (byte) c;
        
        int i = 1;
        try {
            for (; i < len; i++) {
                c = read();
                if (c == -1) break;
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
            if (i == 1) throw ee;
        }
        return i;
    }
    
    public long skip(long n) throws IOException {
        long remaining = n;
        int nr;
        byte[] buffer = new byte[2048];
        while (remaining > 0) {
            nr = read(buffer, 0, (int) Math.min(2048, remaining));
            if (nr < 0) break;
            remaining -= nr;
        }
        return n - remaining;
    }
    
    public int available() throws IOException { return 0; }
    
    public void close() throws IOException {}
    
    public void mark(int readlimit) {}
    
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    public boolean markSupported() { return false; }
    
    public static InputStream nullInputStream() {
        return new InputStream() {
            @Override public int read() { return -1; }
        };
    }
}