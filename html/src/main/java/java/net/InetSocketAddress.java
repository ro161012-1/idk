package java.net;

/**
 * GWT emulation of InetSocketAddress.
 */
public class InetSocketAddress extends SocketAddress {
    private final String hostname;
    private final int port;
    private final InetAddress address;
    
    public InetSocketAddress(int port) { this(null, port); }
    public InetSocketAddress(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.address = null;
    }
    public InetSocketAddress(InetAddress address, int port) {
        this.hostname = address != null ? address.getHostName() : null;
        this.port = port;
        this.address = address;
    }
    
    public String getHostName() { return hostname; }
    public int getPort() { return port; }
    public InetAddress getAddress() { return address; }
    public boolean isUnresolved() { return address == null; }
    public String toString() { return hostname + ":" + port; }
    public boolean equals(Object obj) { return obj instanceof InetSocketAddress && toString().equals(obj.toString()); }
    public int hashCode() { return toString().hashCode(); }
}