package java.util;

public class Date implements Serializable, Cloneable, Comparable<Date> {
    private long time;
    
    public Date() { this.time = System.currentTimeMillis(); }
    public Date(long date) { this.time = date; }
    @Deprecated public Date(int year, int month, int date, int hrs, int min) { this(year, month, date, hrs, min, 0); }
    @Deprecated public Date(int year, int month, int date, int hrs, int min, int sec) { this.time = new java.util.GregorianCalendar(year + 1900, month, date, hrs, min, sec).getTimeInMillis(); }
    @Deprecated public Date(String s) { this.time = parse(s); }
    
    public long getTime() { return time; }
    public void setTime(long time) { this.time = time; }
    public boolean before(Date when) { return time < when.time; }
    public boolean after(Date when) { return time > when.time; }
    public int compareTo(Date anotherDate) { return Long.compare(time, anotherDate.time); }
    public boolean equals(Object obj) { return obj instanceof Date && time == ((Date)obj).time; }
    public int hashCode() { long ht = time; return (int)(ht ^ (ht >>> 32)); }
    public String toString() { return toGMTString(); }
    @Deprecated public String toLocaleString() { return toString(); }
    @Deprecated public String toGMTString() { return new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", java.util.Locale.US).format(this); }
    @Deprecated public int getYear() { return getYearImpl(); }
    @Deprecated public int getMonth() { return getMonthImpl(); }
    @Deprecated public int getDate() { return getDateImpl(); }
    @Deprecated public int getDay() { return getDayImpl(); }
    @Deprecated public int getHours() { return getHoursImpl(); }
    @Deprecated public int getMinutes() { return getMinutesImpl(); }
    @Deprecated public int getSeconds() { return getSecondsImpl(); }
    @Deprecated public void setYear(int year) { setYearImpl(year); }
    @Deprecated public void setMonth(int month) { setMonthImpl(month); }
    @Deprecated public void setDate(int date) { setDateImpl(date); }
    @Deprecated public void setHours(int hours) { setHoursImpl(hours); }
    @Deprecated public void setMinutes(int minutes) { setMinutesImpl(minutes); }
    @Deprecated public void setSeconds(int seconds) { setSecondsImpl(seconds); }
    @Deprecated public static long parse(String s) { return 0; }
    @Deprecated public static long UTC(int year, int month, int date, int hrs, int min, int sec) { return 0; }
    
    private int getYearImpl() { return 0; }
    private int getMonthImpl() { return 0; }
    private int getDateImpl() { return 0; }
    private int getDayImpl() { return 0; }
    private int getHoursImpl() { return 0; }
    private int getMinutesImpl() { return 0; }
    private int getSecondsImpl() { return 0; }
    private void setYearImpl(int year) {}
    private void setMonthImpl(int month) {}
    private void setDateImpl(int date) {}
    private void setHoursImpl(int hours) {}
    private void setMinutesImpl(int minutes) {}
    private void setSecondsImpl(int seconds) {}
}