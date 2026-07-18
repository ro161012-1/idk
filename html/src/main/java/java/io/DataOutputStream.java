package java.io;

/**
 * GWT emulation of DataOutputStream.
 */
public class DataOutputStream extends OutputStream implements DataOutput {
    
    private final OutputStream out;
    private final byte[] buffer = new byte[8];
    private int written = 0;
    
    public DataOutputStream(OutputStream out) { this.out = out; }
    
    @Override public void write(int b) throws IOException { out.write(b); written++; }
    @Override public void write(byte[] b) throws IOException { out.write(b); written += b.length; }
    @Override public void write(byte[] b, int off, int len) throws IOException { out.write(b, off, len); written += len; }
    @Override public void flush() throws IOException { out.flush(); }
    @Override public void close() throws IOException { out.close(); }
    
    public final int size() { return written; }
    
    @Override public final void writeBoolean(boolean v) throws IOException { out.write(v ? 1 : 0); written++; }
    @Override public final void writeByte(int v) throws IOException { out.write(v); written++; }
    @Override public final void writeShort(int v) throws IOException {
        out.write((v >>> 8) & 0xff);
        out.write(v & 0xff);
        written += 2;
    }
    @Override public final void writeChar(int v) throws IOException { writeShort(v); }
    @Override public final void writeInt(int v) throws IOException {
        out.write((v >>> 24) & 0xff);
        out.write((v >>> 16) & 0xff);
        out.write((v >>> 8) & 0xff);
        out.write(v & 0xff);
        written += 4;
    }
    @Override public final void writeLong(long v) throws IOException {
        out.write((int)(v >>> 56) & 0xff);
        out.write((int)(v >>> 48) & 0xff);
        out.write((int)(v >>> 40) & 0xff);
        out.write((int)(v >>> 32) & 0xff);
        out.write((int)(v >>> 24) & 0xff);
        out.write((int)(v >>> 16) & 0xff);
        out.write((int)(v >>> 8) & 0xff);
        out.write((int)v & 0xff);
        written += 8;
    }
    @Override public final void writeFloat(float v) throws IOException { writeInt(Float.floatToIntBits(v)); }
    @Override public final void writeDouble(double v) throws IOException { writeLong(Double.doubleToLongBits(v)); }
    @Override public final void writeBytes(String s) throws IOException {
        for (int i = 0; i < s.length(); i++) out.write((byte)s.charAt(i));
        written += s.length();
    }
    @Override public final void writeChars(String s) throws IOException {
        for (int i = 0; i < s.length(); i++) writeChar(s.charAt(i));
    }
    @Override public final void writeUTF(String s) throws IOException {
        byte[] bytes = encodeUTF(s);
        if (bytes.length > 65535) throw new UTFDataFormatException("encoded string too long");
        writeShort(bytes.length);
        write(bytes);
    }
    
    private byte[] encodeUTF(String s) {
        return s.getBytes();
    }
}