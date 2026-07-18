package java.io;

/**
 * GWT emulation of FileFilter interface.
 */
public interface FileFilter {
    boolean accept(File pathname);
}