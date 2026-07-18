package java.net;

/**
 * GWT emulation of Proxy.
 */
public class Proxy {
    public static final Proxy NO_PROXY = new Proxy(Type.DIRECT, null);
    
    public enum Type { DIRECT, HTTP, SOCKS }
    
    private final Type type;
    private final java.net.SocketAddress address;
    
    public Proxy(Type type, java.net.SocketAddress address) {
        this.type = type;
        this.address = address;
    }
    
    public Type type() { return type; }
    public java.net.SocketAddress address() { return address; }
    public boolean equals(Object obj) { return obj instanceof Proxy && type == ((Proxy)obj).type; }
    public int hashCode() { return type.hashCode(); }
    public String toString() { return "Proxy[" + type + "]"; }
}