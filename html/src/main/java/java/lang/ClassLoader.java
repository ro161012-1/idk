package java.lang;

public abstract class ClassLoader {
    private final ClassLoader parent;
    
    protected ClassLoader() { this.parent = getSystemClassLoader(); }
    protected ClassLoader(ClassLoader parent) { this.parent = parent; }
    
    public static ClassLoader getSystemClassLoader() { return new SystemClassLoader(); }
    public ClassLoader getParent() { return parent; }
    
    public Class<?> loadClass(String name) throws ClassNotFoundException { return loadClass(name, false); }
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            try { c = parent != null ? parent.loadClass(name) : findBootstrapClass(name); }
            catch (ClassNotFoundException e) { c = findClass(name); }
        }
        if (resolve) resolveClass(c);
        return c;
    }
    
    protected Class<?> findClass(String name) throws ClassNotFoundException { throw new ClassNotFoundException(name); }
    protected Class<?> findLoadedClass(String name) { return null; }
    protected Class<?> findBootstrapClass(String name) { return null; }
    protected void resolveClass(Class<?> c) {}
    protected Class<?> defineClass(String name, byte[] b, int off, int len) { return null; }
    protected Class<?> defineClass(String name, java.nio.ByteBuffer b) { return null; }
    protected Package definePackage(String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, String sealBase) { return null; }
    protected Package getPackage(String name) { return null; }
    protected Package[] getPackages() { return new Package[0]; }
    public URL getResource(String name) { return null; }
    public java.io.InputStream getResourceAsStream(String name) { return null; }
    public Enumeration<URL> getResources(String name) { return null; }
    public void setDefaultAssertionStatus(boolean enabled) {}
    public void setPackageAssertionStatus(String packageName, boolean enabled) {}
    public void setClassAssertionStatus(String className, boolean enabled) {}
    public void clearAssertionStatus() {}
    
    static class SystemClassLoader extends ClassLoader {
        protected Class<?> findClass(String name) { return null; }
    }
}
