package java.math;

import java.util.Random;

public class BigInteger extends Number implements Comparable<BigInteger> {
    private final long value;
    
    public BigInteger(String val) { this.value = Long.parseLong(val); }
    public BigInteger(String val, int radix) { this.value = Long.parseLong(val, radix); }
    public BigInteger(long val) { this.value = val; }
    
    public static BigInteger valueOf(long val) { return new BigInteger(val); }
    public static BigInteger ZERO = new BigInteger(0);
    public static BigInteger ONE = new BigInteger(1);
    public static BigInteger TEN = new BigInteger(10);
    
    public BigInteger add(BigInteger val) { return new BigInteger(value + val.value); }
    public BigInteger subtract(BigInteger val) { return new BigInteger(value - val.value); }
    public BigInteger multiply(BigInteger val) { return new BigInteger(value * val.value); }
    public BigInteger divide(BigInteger val) { return new BigInteger(value / val.value); }
    public BigInteger mod(BigInteger val) { return new BigInteger(value % val.value); }
    public BigInteger abs() { return value >= 0 ? this : new BigInteger(-value); }
    public BigInteger negate() { return new BigInteger(-value); }
    public int signum() { return Long.signum(value); }
    public int compareTo(BigInteger val) { return Long.compare(value, val.value); }
    public boolean equals(Object x) { return x instanceof BigInteger && value == ((BigInteger)x).value; }
    public int hashCode() { return Long.hashCode(value); }
    public String toString() { return Long.toString(value); }
    public String toString(int radix) { return Long.toString(value, radix); }
    
    @Override public int intValue() { return (int)value; }
    @Override public long longValue() { return value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }
    
    public BigInteger and(BigInteger val) { return new BigInteger(value & val.value); }
    public BigInteger or(BigInteger val) { return new BigInteger(value | val.value); }
    public BigInteger xor(BigInteger val) { return new BigInteger(value ^ val.value); }
    public BigInteger not() { return new BigInteger(~value); }
    public BigInteger shiftLeft(int n) { return new BigInteger(value << n); }
    public BigInteger shiftRight(int n) { return new BigInteger(value >> n); }
    public int bitLength() { return 64 - Long.numberOfLeadingZeros(value != 0 ? value : 1); }
    public int bitCount() { return Long.bitCount(value); }
    public boolean testBit(int n) { return (value & (1L << n)) != 0; }
    public BigInteger setBit(int n) { return new BigInteger(value | (1L << n)); }
    public BigInteger clearBit(int n) { return new BigInteger(value & ~(1L << n)); }
    public BigInteger flipBit(int n) { return new BigInteger(value ^ (1L << n)); }
    public int getLowestSetBit() { return value == 0 ? -1 : Long.numberOfTrailingZeros(value); }
    
    public static BigInteger probablePrime(int bitLength, Random rnd) { return BigInteger.ONE; }
}