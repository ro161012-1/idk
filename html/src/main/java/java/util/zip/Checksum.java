package java.util.zip;

/**
 * GWT emulation of Checksum interface.
 */
public interface Checksum {
    void update(int b);
    void update(byte[] b, int off, int len);
    void update(byte[] b);
    long getValue();
    void reset();
}