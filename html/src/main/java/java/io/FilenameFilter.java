package java.io;

/**
 * GWT emulation of FilenameFilter interface.
 */
public interface FilenameFilter {
    boolean accept(File dir, String name);
}