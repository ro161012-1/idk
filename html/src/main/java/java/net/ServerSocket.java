package java.net;

/**
 * GWT emulation of ServerSocket - not really usable in browser.
 */
public class ServerSocket implements Closeable {
    private boolean closed = false;
    
    public ServerSocket() {}
    public ServerSocket(int port) throws IOException {}
    public ServerSocket(int port, int backlog) throws IOException {}
    public ServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {}
    
    public Socket accept() throws IOException { return null; }
    public void close() throws IOException { closed = true; }
    public boolean isClosed() { return closed; }
    public InetAddress getInetAddress() { return InetAddress.getLocalHost(); }
    public int getLocalPort() { return 0; }
    public void setSoTimeout(int timeout) {}
    public int getSoTimeout() { return 0; }
}