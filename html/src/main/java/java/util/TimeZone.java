package java.util;

public abstract class TimeZone implements Serializable, Cloneable {
    private String ID;
    
    public TimeZone() { this.ID = "GMT"; }
    
    public static TimeZone getDefault() { return new SimpleTimeZone(0, "GMT"); }
    public static TimeZone getTimeZone(String ID) { return new SimpleTimeZone(0, ID); }
    public static String[] getAvailableIDs() { return new String[]{"GMT", "UTC"}; }
    public static String[] getAvailableIDs(int rawOffset) { return new String[]{"GMT"}; }
    
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }
    public abstract int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds);
    public abstract int getRawOffset();
    public abstract void setRawOffset(int offsetMillis);
    public abstract boolean useDaylightTime();
    public abstract boolean observesDaylightTime();
    public abstract boolean inDaylightTime(Date date);
    
    public int getOffset(Date date) { return getOffset(0, 0, 0, 0, 0, 0); }
    public boolean hasSameRules(TimeZone other) { return false; }
    public Object clone() { try { return super.clone(); } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
    
    static class SimpleTimeZone extends TimeZone {
        private int rawOffset;
        public SimpleTimeZone(int rawOffset, String ID) { this.rawOffset = rawOffset; this.ID = ID; }
        @Override public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) { return rawOffset; }
        @Override public int getRawOffset() { return rawOffset; }
        @Override public void setRawOffset(int offsetMillis) { rawOffset = offsetMillis; }
        @Override public boolean useDaylightTime() { return false; }
        @Override public boolean observesDaylightTime() { return false; }
        @Override public boolean inDaylightTime(Date date) { return false; }
    }
}