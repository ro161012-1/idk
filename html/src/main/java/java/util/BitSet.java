package java.util;

public class BitSet implements Cloneable, Serializable {
    private long[] words;
    private int size = 64;
    
    public BitSet() { this.words = new long[1]; }
    public BitSet(int nbits) { if (nbits < 0) throw new NegativeArraySizeException(); this.words = new long[(nbits + 63) >>> 6]; }
    
    public void set(int bitIndex) { if (bitIndex < 0) throw new IndexOutOfBoundsException(); ensureCapacity(bitIndex); words[bitIndex >> 6] |= (1L << bitIndex); }
    public void set(int bitIndex, boolean value) { if (value) set(bitIndex); else clear(bitIndex); }
    public void clear(int bitIndex) { if (bitIndex < 0) throw new IndexOutOfBoundsException(); int word = bitIndex >> 6; if (word < words.length) words[word] &= ~(1L << bitIndex); }
    public void clear() { for (int i = 0; i < words.length; i++) words[i] = 0; }
    public boolean get(int bitIndex) { if (bitIndex < 0) throw new IndexOutOfBoundsException(); int word = bitIndex >> 6; return word < words.length && (words[word] & (1L << bitIndex)) != 0; }
    public int length() { return size; }
    public boolean isEmpty() { for (long w : words) if (w != 0) return false; return true; }
    public int cardinality() { int count = 0; for (long w : words) count += Long.bitCount(w); return count; }
    public void and(BitSet set) { for (int i = Math.min(words.length, set.words.length) - 1; i >= 0; i--) words[i] &= set.words[i]; }
    public void or(BitSet set) { ensureCapacity(set.words.length * 64); for (int i = 0; i < set.words.length; i++) words[i] |= set.words[i]; }
    public void xor(BitSet set) { ensureCapacity(set.words.length * 64); for (int i = 0; i < set.words.length; i++) words[i] ^= set.words[i]; }
    public void andNot(BitSet set) { for (int i = 0; i < Math.min(words.length, set.words.length); i++) words[i] &= ~set.words[i]; }
    public boolean intersects(BitSet set) { for (int i = 0; i < Math.min(words.length, set.words.length); i++) if ((words[i] & set.words[i]) != 0) return true; return false; }
    public int nextSetBit(int fromIndex) { if (fromIndex < 0) throw new IndexOutOfBoundsException(); for (int i = fromIndex >> 6; i < words.length; i++) { long word = words[i] & (~0L << (fromIndex & 63)); if (word != 0) return (i << 6) + Long.numberOfTrailingZeros(word); fromIndex = 0; } return -1; }
    public int nextClearBit(int fromIndex) { if (fromIndex < 0) throw new IndexOutOfBoundsException(); for (int i = fromIndex >> 6; i < words.length; i++) { long word = ~words[i] & (~0L << (fromIndex & 63)); if (word != 0) return (i << 6) + Long.numberOfTrailingZeros(word); fromIndex = 0; } return -1; }
    public int previousSetBit(int fromIndex) { if (fromIndex < 0) return -1; for (int i = fromIndex >> 6; i >= 0; i--) { long word = words[i] & (~(-1L << (fromIndex & 63) + 1)); if (word != 0) return (i << 6) + 63 - Long.numberOfLeadingZeros(word); fromIndex = 63; } return -1; }
    public int previousClearBit(int fromIndex) { if (fromIndex < 0) return -1; for (int i = fromIndex >> 6; i >= 0; i--) { long word = ~words[i] & (~(-1L << (fromIndex & 63) + 1)); if (word != 0) return (i << 6) + 63 - Long.numberOfLeadingZeros(word); fromIndex = 63; } return -1; }
    
    private void ensureCapacity(int bitIndex) { int wordIndex = bitIndex >> 6; if (wordIndex >= words.length) { long[] newWords = new long[wordIndex + 1]; System.arraycopy(words, 0, newWords, 0, words.length); words = newWords; } }
    
    @Override public Object clone() { try { BitSet copy = (BitSet) super.clone(); copy.words = words.clone(); return copy; } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
    @Override public boolean equals(Object obj) { if (!(obj instanceof BitSet)) return false; BitSet other = (BitSet) obj; int min = Math.min(words.length, other.words.length); for (int i = 0; i < min; i++) if (words[i] != other.words[i]) return false; for (int i = min; i < words.length; i++) if (words[i] != 0) return false; for (int i = min; i < other.words.length; i++) if (other.words[i] != 0) return false; return true; }
    @Override public int hashCode() { int h = 1234; for (int i = words.length - 1; i >= 0; i--) h ^= (words[i] * (i + 1)); return h; }
    @Override public String toString() { StringBuilder sb = new StringBuilder(); sb.append("{"); boolean first = true; for (int i = nextSetBit(0); i >= 0; i = nextSetBit(i + 1)) { if (!first) sb.append(", "); sb.append(i); first = false; } sb.append("}"); return sb.toString(); }
}