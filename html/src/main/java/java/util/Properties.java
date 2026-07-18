package java.util;

public class Properties extends Hashtable<Object, Object> {
    public Properties() { super(); }
    public Properties(Properties defaults) { super(); this.defaults = defaults; }
    
    private Properties defaults;
    
    public String getProperty(String key) { return (String) get(key); }
    public String getProperty(String key, String defaultValue) { String val = getProperty(key); return val == null ? defaultValue : val; }
    public Object setProperty(String key, String value) { return put(key, value); }
    public void load(InputStream inStream) throws IOException {}
    public void store(OutputStream out, String comments) throws IOException {}
    public Enumeration<?> propertyNames() { return keys(); }
}
