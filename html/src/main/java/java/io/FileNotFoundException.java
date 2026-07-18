package java.io;

/**
 * GWT emulation of FileNotFoundException.
 */
public class FileNotFoundException extends IOException {
    public FileNotFoundException() { super(); }
    public FileNotFoundException(String message) { super(message); }
}