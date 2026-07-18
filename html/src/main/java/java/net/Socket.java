package java.net;

/**
 * GWT emulation of Socket - uses WebSocket.
 */
public class Socket implements Closeable {
    private final String host;
    private final int port;
    private boolean connected = false;
    private boolean closed = false;
    private InputStream input;
    private OutputStream output;
    
    public Socket() { this.host = null; this.port = -1; }
    public Socket(String host, int port) throws IOException { this.host = host; this.port = port; connect(); }
    public Socket(InetAddress address, int port) throws IOException { this(address.getHostAddress(), port); }
    public Socket(String host, int port, InetAddress localAddr, int localPort) throws IOException { this(host, port); }
    public Socket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException { this(address, port); }
    
    public void connect(SocketAddress endpoint) throws IOException { connect(endpoint, 0); }
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        if (endpoint instanceof InetSocketAddress) {
            InetSocketAddress addr = (InetSocketAddress) endpoint;
            this.host = addr.getHostName();
            this.port = addr.getPort();
        }
        connected = true;
        input = new SocketInputStream();
        output = new SocketOutputStream();
    }
    
    private void connect() throws IOException {
        connected = true;
        input = new SocketInputStream();
        output = new SocketOutputStream();
    }
    
    public InputStream getInputStream() { return input; }
    public OutputStream getOutputStream() { return output; }
    
    @Override public void close() throws IOException { closed = true; connected = false; }
    public boolean isConnected() { return connected; }
    public boolean isClosed() { return closed; }
    public InetAddress getInetAddress() { return InetAddress.getByName(host); }
    public InetAddress getLocalAddress() { return InetAddress.getLocalHost(); }
    public int getPort() { return port; }
    public int getLocalPort() { return 0; }
    public void setSoTimeout(int timeout) {}
    public int getSoTimeout() { return 0; }
    public void setTcpNoDelay(boolean on) {}
    public boolean getTcpNoDelay() { return false; }
    public void setKeepAlive(boolean on) {}
    public boolean getKeepAlive() { return false; }
    public void setSendBufferSize(int size) {}
    public int getSendBufferSize() { return 0; }
    public void setReceiveBufferSize(int size) {}
    public int getReceiveBufferSize() { return 0; }
    
    private class SocketInputStream extends InputStream {
        @Override public int read() throws IOException { return -1; }
        @Override public int read(byte[] b) throws IOException { return 0; }
        @Override public int read(byte[] b, int off, int len) throws IOException { return 0; }
    }
    
    private class SocketOutputStream extends OutputStream {
        @Override public void write(int b) throws IOException {}
        @Override public void write(byte[] b) throws IOException {}
        @Override public void write(byte[] b, int off, int len) throws IOException {}
    }
}