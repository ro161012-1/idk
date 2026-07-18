package java.net;

/**
 * GWT emulation of InetAddress.
 */
public class InetAddress implements Serializable {
    private final String hostName;
    private final String hostAddress;
    
    private InetAddress(String hostName, String hostAddress) {
        this.hostName = hostName;
        this.hostAddress = hostAddress;
    }
    
    public static InetAddress getLocalHost() throws UnknownHostException {
        return new InetAddress("localhost", "127.0.0.1");
    }
    
    public static InetAddress getByName(String host) throws UnknownHostException {
        return new InetAddress(host, host);
    }
    
    public static InetAddress[] getAllByName(String host) throws UnknownHostException {
        return new InetAddress[]{getByName(host)};
    }
    
    public String getHostName() { return hostName; }
    public String getHostAddress() { return hostAddress; }
    public String getCanonicalHostName() { return hostName; }
    public byte[] getAddress() { 
        String[] parts = hostAddress.split("\\.");
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) bytes[i] = (byte)Integer.parseInt(parts[i]);
        return bytes;
    }
    public boolean isLoopbackAddress() { return "127.0.0.1".equals(hostAddress) || "::1".equals(hostAddress); }
    public boolean isAnyLocalAddress() { return "0.0.0.0".equals(hostAddress) || "::".equals(hostAddress); }
    public String toString() { return hostName + "/" + hostAddress; }
    public boolean equals(Object obj) { return obj instanceof InetAddress && hostAddress.equals(((InetAddress)obj).hostAddress); }
    public int hashCode() { return hostAddress.hashCode(); }
}