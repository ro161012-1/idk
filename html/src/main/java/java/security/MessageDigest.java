package java.security;

/**
 * GWT emulation of MessageDigest - minimal SHA/MD5 for Mindustry.
 */
public class MessageDigest {
    
    private final String algorithm;
    private final java.util.ArrayList<Byte> buffer = new java.util.ArrayList<>();
    
    protected MessageDigest(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public static MessageDigest getInstance(String algorithm) throws NoSuchAlgorithmException {
        if ("SHA-256".equalsIgnoreCase(algorithm) || "SHA-1".equalsIgnoreCase(algorithm) 
            || "MD5".equalsIgnoreCase(algorithm)) {
            return new MessageDigest(algorithm);
        }
        throw new NoSuchAlgorithmException(algorithm);
    }
    
    public void update(byte input) { buffer.add(input); }
    public void update(byte[] input) { 
        if (input != null) for (byte b : input) buffer.add(b); 
    }
    public void update(byte[] input, int offset, int len) {
        for (int i = 0; i < len; i++) buffer.add(input[offset + i]);
    }
    
    public byte[] digest() {
        byte[] result = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) result[i] = buffer.get(i);
        buffer.clear();
        // Return dummy hash - not cryptographically secure
        return result;
    }
    
    public byte[] digest(byte[] input) {
        update(input);
        return digest();
    }
    
    public String getAlgorithm() { return algorithm; }
    public int getDigestLength() { return 32; }
    public void reset() { buffer.clear(); }
    
    public static boolean isEqual(byte[] digesta, byte[] digestb) {
        if (digesta == digestb) return true;
        if (digesta == null || digestb == null) return false;
        if (digesta.length != digestb.length) return false;
        for (int i = 0; i < digesta.length; i++) if (digesta[i] != digestb[i]) return false;
        return true;
    }
}