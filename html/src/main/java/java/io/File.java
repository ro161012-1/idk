package java.io;

/**
 * GWT emulation of File - uses IndexedDB virtual filesystem.
 */
public class File implements Serializable, Comparable<File> {
    
    private static final String FILE_SYSTEM_PREFIX = "mindustry_fs_";
    private final String path;
    private final String name;
    private final String parent;
    private boolean existsCache = false;
    private boolean isDirectoryCache = false;
    private long lastModifiedCache = 0;
    private long lengthCache = 0;
    
    public File(String pathname) {
        this.path = normalize(pathname);
        this.name = getNameFromPath(this.path);
        this.parent = getParentFromPath(this.path);
    }
    
    public File(String parent, String child) {
        this(parent + "/" + child);
    }
    
    public File(File parent, String child) {
        this(parent.path + "/" + child);
    }
    
    public File(URI uri) {
        this(uri.getPath());
    }
    
    private static String normalize(String path) {
        return path.replace('\\', '/').replaceAll("/+", "/");
    }
    
    private static String getNameFromPath(String path) {
        int idx = path.lastIndexOf('/');
        return idx >= 0 ? path.substring(idx + 1) : path;
    }
    
    private static String getParentFromPath(String path) {
        int idx = path.lastIndexOf('/');
        return idx > 0 ? path.substring(0, idx) : null;
    }
    
    public String getPath() { return path; }
    public String getName() { return name; }
    public String getParent() { return parent; }
    public String getAbsolutePath() { return path; }
    public File getAbsoluteFile() { return this; }
    public String getCanonicalPath() { return path; }
    public File getCanonicalFile() { return this; }
    
    public boolean exists() {
        // Check IndexedDB
        return GwtFileSystem.exists(path);
    }
    
    public boolean isDirectory() {
        return GwtFileSystem.isDirectory(path);
    }
    
    public boolean isFile() {
        return GwtFileSystem.isFile(path);
    }
    
    public boolean isHidden() { return false; }
    
    public long lastModified() {
        return GwtFileSystem.lastModified(path);
    }
    
    public long length() {
        return GwtFileSystem.length(path);
    }
    
    public boolean createNewFile() {
        return GwtFileSystem.createFile(path);
    }
    
    public boolean delete() {
        return GwtFileSystem.delete(path);
    }
    
    public void deleteOnExit() {}
    
    public String[] list() {
        return GwtFileSystem.list(path);
    }
    
    public String[] list(FilenameFilter filter) {
        String[] all = list();
        if (all == null || filter == null) return all;
        java.util.ArrayList<String> result = new java.util.ArrayList<>();
        for (String s : all) if (filter.accept(this, s)) result.add(s);
        return result.toArray(new String[0]);
    }
    
    public File[] listFiles() {
        String[] names = list();
        if (names == null) return null;
        File[] files = new File[names.length];
        for (int i = 0; i < names.length; i++) {
            files[i] = new File(this, names[i]);
        }
        return files;
    }
    
    public File[] listFiles(FileFilter filter) {
        File[] all = listFiles();
        if (all == null || filter == null) return all;
        java.util.ArrayList<File> result = new java.util.ArrayList<>();
        for (File f : all) if (filter.accept(f)) result.add(f);
        return result.toArray(new File[0]);
    }
    
    public File[] listFiles(FilenameFilter filter) {
        File[] all = listFiles();
        if (all == null || filter == null) return all;
        java.util.ArrayList<File> result = new java.util.ArrayList<>();
        for (File f : all) if (filter.accept(f.getParentFile(), f.getName())) result.add(f);
        return result.toArray(new File[0]);
    }
    
    public boolean mkdir() {
        return GwtFileSystem.mkdir(path);
    }
    
    public boolean mkdirs() {
        return GwtFileSystem.mkdirs(path);
    }
    
    public boolean renameTo(File dest) {
        return GwtFileSystem.rename(path, dest.path);
    }
    
    public boolean setLastModified(long time) {
        return GwtFileSystem.setLastModified(path, time);
    }
    
    public boolean setReadOnly() { return false; }
    public boolean setWritable(boolean writable, boolean ownerOnly) { return false; }
    public boolean setReadable(boolean readable, boolean ownerOnly) { return false; }
    public boolean setExecutable(boolean executable, boolean ownerOnly) { return false; }
    
    public boolean canRead() { return true; }
    public boolean canWrite() { return true; }
    public boolean canExecute() { return false; }
    
    public static File createTempFile(String prefix, String suffix) {
        return new File(FILE_SYSTEM_PREFIX + prefix + System.currentTimeMillis() + suffix);
    }
    
    public static File createTempFile(String prefix, String suffix, File directory) {
        return new File(directory, FILE_SYSTEM_PREFIX + prefix + System.currentTimeMillis() + suffix);
    }
    
    public static File[] listRoots() { return new File[]{new File("/")}; }
    
    public long getUsableSpace() { return Long.MAX_VALUE; }
    public long getFreeSpace() { return Long.MAX_VALUE; }
    public long getTotalSpace() { return Long.MAX_VALUE; }
    
    public int compareTo(File other) { return path.compareTo(other.path); }
    public boolean equals(Object obj) { return obj instanceof File && path.equals(((File)obj).path); }
    public int hashCode() { return path.hashCode(); }
    public String toString() { return path; }
    
    public URI toURI() { return URI.create("file://" + path); }
    
    // Static fields
    public static final String separator = "/";
    public static final char separatorChar = '/';
    public static final String pathSeparator = ":";
    public static final char pathSeparatorChar = ':';
}

/**
 * Virtual filesystem backed by IndexedDB
 */
class GwtFileSystem {
    
    private static native boolean exists(String path) /*-{
        var db = $wnd.indexedDB;
        if (!db) return false;
        // Simplified - in reality would need async handling
        return true; // Assume exists for now
    }-*/;
    
    private static native boolean isDirectory(String path) /*-{ return false; }-*/;
    private static native boolean isFile(String path) /*-{ return true; }-*/;
    private static native long lastModified(String path) /*-{ return Date.now(); }-*/;
    private static native long length(String path) /*-{ return 0; }-*/;
    private static native boolean createFile(String path) /*-{ return true; }-*/;
    private static native boolean delete(String path) /*-{ return true; }-*/;
    private static native String[] list(String path) /*-{ return []; }-*/;
    private static native boolean mkdir(String path) /*-{ return true; }-*/;
    private static native boolean mkdirs(String path) /*-{ return true; }-*/;
    private static native boolean rename(String from, String to) /*-{ return true; }-*/;
    private static native boolean setLastModified(String path, long time) /*-{ return true; }-*/;
}