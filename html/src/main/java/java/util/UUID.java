package java.util;

public class UUID implements Serializable, Comparable<UUID> {
    private final long mostSigBits;
    private final long leastSigBits;
    
    public UUID(long mostSigBits, long leastSigBits) { this.mostSigBits = mostSigBits; this.leastSigBits = leastSigBits; }
    
    public static UUID randomUUID() {
        Random rnd = new Random();
        long msb = rnd.nextLong();
        long lsb = rnd.nextLong();
        msb = (msb & 0xFFFFFFFFFFFF0FFFL) | 0x0000000000004000L; // version 4
        lsb = (lsb & 0x3FFFFFFFFFFFFFFFL) | 0x8000000000000000L; // variant 2
        return new UUID(msb, lsb);
    }
    
    public String toString() {
        return digits(mostSigBits >> 32, 8) + "-" + digits(mostSigBits >> 16, 4) + "-" + digits(mostSigBits, 4) + "-" + digits(leastSigBits >> 48, 4) + "-" + digits(leastSigBits, 12);
    }
    
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
    
    public long getMostSignificantBits() { return mostSigBits; }
    public long getLeastSignificantBits() { return leastSigBits; }
    public int hashCode() { return (int)((mostSigBits >> 32) ^ mostSigBits ^ (leastSigBits >> 32) ^ leastSigBits); }
    public boolean equals(Object obj) { return obj instanceof UUID && mostSigBits == ((UUID)obj).mostSigBits && leastSigBits == ((UUID)obj).leastSigBits; }
    public int compareTo(UUID val) { return Long.compare(mostSigBits, val.mostSigBits) != 0 ? Long.compare(mostSigBits, val.mostSigBits) : Long.compare(leastSigBits, val.leastSigBits); }
}
