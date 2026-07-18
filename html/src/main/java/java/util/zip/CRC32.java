package java.util.zip;

/**
 * GWT emulation of CRC32 - minimal implementation.
 */
public class CRC32 implements Checksum {
    private long crc = 0;
    
    private static final int[] CRC_TABLE = new int[256];
    static {
        for (int i = 0; i < 256; i++) {
            int c = i;
            for (int j = 0; j < 8; j++) {
                c = (c & 1) == 1 ? 0xEDB88320 ^ (c >>> 1) : c >>> 1;
            }
            CRC_TABLE[i] = c;
        }
    }
    
    public void update(int b) {
        crc = (crc >>> 8) ^ CRC_TABLE[(int)(crc ^ b) & 0xFF];
    }
    
    public void update(byte[] b, int off, int len) {
        for (int i = 0; i < len; i++) {
            update(b[off + i]);
        }
    }
    
    public void update(byte[] b) { update(b, 0, b.length); }
    
    public long getValue() { return crc & 0xFFFFFFFFL; }
    public void reset() { crc = 0; }
}