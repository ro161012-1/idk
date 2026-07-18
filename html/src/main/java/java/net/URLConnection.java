package java.net;

/**
 * GWT emulation of URLConnection.
 */
public class URLConnection {
    protected final URL url;
    protected boolean connected = false;
    protected boolean doInput = true;
    protected boolean doOutput = false;
    protected int connectTimeout = 0;
    protected int readTimeout = 0;
    
    protected URLConnection(URL url) { this.url = url; }
    
    public void connect() throws IOException { connected = true; }
    public boolean getDoInput() { return doInput; }
    public void setDoInput(boolean doInput) { this.doInput = doInput; }
    public boolean getDoOutput() { return doOutput; }
    public void setDoOutput(boolean doOutput) { this.doOutput = doOutput; }
    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int timeout) { connectTimeout = timeout; }
    public int getReadTimeout() { return readTimeout; }
    public void setReadTimeout(int timeout) { readTimeout = timeout; }
    public String getContentType() { return null; }
    public long getContentLength() { return -1; }
    public long getLastModified() { return 0; }
    public String getHeaderField(String name) { return null; }
    public String getHeaderFieldKey(int n) { return null; }
    public java.util.Map<String, java.util.List<String>> getHeaderFields() { return null; }
    public InputStream getInputStream() throws IOException { return null; }
    public OutputStream getOutputStream() throws IOException { return null; }
}