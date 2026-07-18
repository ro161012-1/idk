package java.net;

/**
 * GWT emulation of URL.
 */
public class URL {
    private final String protocol;
    private final String host;
    private final int port;
    private final String file;
    private final String ref;
    
    public URL(String spec) throws MalformedURLException {
        // Simplified parsing
        this.protocol = "";
        this.host = "";
        this.port = -1;
        this.file = spec;
        this.ref = null;
    }
    
    public URL(String protocol, String host, int port, String file) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.file = file;
        this.ref = null;
    }
    
    public URL(String protocol, String host, String file) {
        this(protocol, host, -1, file);
    }
    
    public URL(URL context, String spec) throws MalformedURLException {
        this(spec);
    }
    
    public String getProtocol() { return protocol; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public int getDefaultPort() { return -1; }
    public String getFile() { return file; }
    public String getPath() { return file; }
    public String getRef() { return ref; }
    public String getAuthority() { return host + (port > 0 ? ":" + port : ""); }
    public String getQuery() { return null; }
    public String getUserInfo() { return null; }
    
    public String toString() { return protocol + "://" + getAuthority() + file; }
    public String toExternalForm() { return toString(); }
    
    public URLConnection openConnection() throws IOException {
        return new URLConnection(this);
    }
    
    public URLConnection openConnection(Proxy proxy) throws IOException {
        return openConnection();
    }
    
    public boolean sameFile(URL other) { return equals(other); }
    public boolean equals(Object obj) { return obj instanceof URL && toString().equals(obj.toString()); }
    public int hashCode() { return toString().hashCode(); }
}