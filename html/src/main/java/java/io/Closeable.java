package java.io;

/**
 * GWT emulation of Closeable interface.
 */
public interface Closeable extends AutoCloseable {
    void close() throws IOException;
}