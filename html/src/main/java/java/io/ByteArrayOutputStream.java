package java.io;

/**
 * GWT emulation of ByteArrayOutputStream.
 */
public class ByteArrayOutputStream extends OutputStream {
    
    protected byte[] buf;
    protected int count = 0;
    
    public ByteArrayOutputStream() { this(32); }
    public ByteArrayOutputStream(int size) { 
        if (size < 0) throw new IllegalArgumentException("Negative initial size");
        buf = new byte[size]; 
    }
    
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > buf.length) {
            int newCapacity = buf.length * 2;
            if (newCapacity < minCapacity) newCapacity = minCapacity;
            byte[] newBuf = new byte[newCapacity];
            System.arraycopy(buf, 0, newBuf, 0, count);
            buf = newBuf;
        }
    }
    
    @Override
    public synchronized void write(int b) {
        ensureCapacity(count + 1);
        buf[count++] = (byte) b;
    }
    
    @Override
    public synchronized void write(byte[] b, int off, int len) {
        if (b == null) throw new NullPointerException();
        if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException();
        ensureCapacity(count + len);
        System.arraycopy(b, off, buf, count, len);
        count += len;
    }
    
    public synchronized void writeTo(OutputStream out) throws IOException {
        out.write(buf, 0, count);
    }
    
    public synchronized void reset() { count = 0; }
    
    public synchronized byte[] toByteArray() {
        byte[] result = new byte[count];
        System.arraycopy(buf, 0, result, 0, count);
        return result;
    }
    
    public synchronized int size() { return count; }
    
    public synchronized String toString() {
        return new String(buf, 0, count);
    }
    
    public synchronized String toString(String charsetName) throws UnsupportedEncodingException {
        return new String(buf, 0, count, charsetName);
    }
    
    public synchronized void close() {}
}