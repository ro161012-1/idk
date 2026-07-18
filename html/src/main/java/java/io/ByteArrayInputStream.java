package java.io;

/**
 * GWT emulation of ByteArrayInputStream.
 */
public class ByteArrayInputStream extends InputStream {
    
    protected byte[] buf;
    protected int pos = 0;
    protected int mark = 0;
    protected int count;
    
    public ByteArrayInputStream(byte[] buf) {
        this.buf = buf;
        this.count = buf.length;
    }
    
    public ByteArrayInputStream(byte[] buf, int offset, int length) {
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.mark = offset;
    }
    
    @Override
    public synchronized int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }
    
    @Override
    public synchronized int read(byte[] b, int off, int len) {
        if (b == null) throw new NullPointerException();
        if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException();
        if (pos >= count) return -1;
        int avail = count - pos;
        if (len > avail) len = avail;
        if (len <= 0) return 0;
        System.arraycopy(buf, pos, b, off, len);
        pos += len;
        return len;
    }
    
    @Override
    public synchronized long skip(long n) {
        long k = count - pos;
        if (n < k) k = n < 0 ? 0 : n;
        pos += k;
        return k;
    }
    
    @Override
    public synchronized int available() {
        return count - pos;
    }
    
    @Override
    public boolean markSupported() { return true; }
    
    @Override
    public synchronized void mark(int readlimit) { mark = pos; }
    
    @Override
    public synchronized void reset() { pos = mark; }
    
    @Override
    public void close() {}
}