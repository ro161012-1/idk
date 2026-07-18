package java.util;

public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int WEEK_OF_YEAR = 3;
    public static final int WEEK_OF_MONTH = 4;
    public static final int DATE = 5;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_YEAR = 6;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int AM_PM = 9;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;
    public static final int ZONE_OFFSET = 15;
    public static final int DST_OFFSET = 16;
    public static final int FIELD_COUNT = 17;
    
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    
    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int APRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;
    
    protected int[] fields = new int[FIELD_COUNT];
    protected boolean[] isSet = new boolean[FIELD_COUNT];
    protected long time;
    protected boolean isTimeSet = false;
    protected boolean areFieldsSet = false;
    protected boolean lenient = true;
    protected TimeZone zone;
    protected int firstDayOfWeek = SUNDAY;
    protected int minimalDaysInFirstWeek = 1;
    
    public Calendar() { this(TimeZone.getDefault(), Locale.getDefault()); }
    public Calendar(TimeZone zone, Locale aLocale) { this.zone = zone; }
    
    public static Calendar getInstance() { return new GregorianCalendar(); }
    public static Calendar getInstance(TimeZone zone) { return new GregorianCalendar(zone); }
    public static Calendar getInstance(Locale aLocale) { return new GregorianCalendar(aLocale); }
    public static Calendar getInstance(TimeZone zone, Locale aLocale) { return new GregorianCalendar(zone, aLocale); }
    
    public abstract void add(int field, int amount);
    public abstract void roll(int field, boolean up);
    public abstract void roll(int field, int amount);
    public abstract int getMinimum(int field);
    public abstract int getMaximum(int field);
    public abstract int getGreatestMinimum(int field);
    public abstract int getLeastMaximum(int field);
    
    public final void set(int field, int value) { fields[field] = value; isSet[field] = true; isTimeSet = false; }
    public final void set(int year, int month, int date) { set(YEAR, year); set(MONTH, month); set(DATE, date); }
    public final void set(int year, int month, int date, int hourOfDay, int minute) { set(year, month, date, hourOfDay, minute, 0); }
    public final void set(int year, int month, int date, int hourOfDay, int minute, int second) { set(year, month, date); set(HOUR_OF_DAY, hourOfDay); set(MINUTE, minute); set(SECOND, second); }
    
    public final int get(int field) { if (!areFieldsSet) computeFields(); return fields[field]; }
    public final boolean isSet(int field) { return isSet[field]; }
    
    public final long getTimeInMillis() { if (!isTimeSet) updateTime(); return time; }
    public final Date getTime() { return new Date(getTimeInMillis()); }
    public final void setTime(Date date) { time = date.getTime(); isTimeSet = true; areFieldsSet = false; }
    public final void setTimeInMillis(long millis) { time = millis; isTimeSet = true; areFieldsSet = false; }
    
    public final TimeZone getTimeZone() { return zone; }
    public final void setTimeZone(TimeZone value) { zone = value; }
    
    public int getFirstDayOfWeek() { return firstDayOfWeek; }
    public void setFirstDayOfWeek(int value) { firstDayOfWeek = value; }
    public int getMinimalDaysInFirstWeek() { return minimalDaysInFirstWeek; }
    public void setMinimalDaysInFirstWeek(int value) { minimalDaysInFirstWeek = value; }
    
    public boolean isLenient() { return lenient; }
    public void setLenient(boolean lenient) { this.lenient = lenient; }
    
    protected abstract void computeFields();
    protected abstract void computeTime();
    
    public void computeFields() { areFieldsSet = true; }
    public void computeTime() { isTimeSet = true; }
    
    public void add(int field, int amount) { set(field, get(field) + amount); }
    public void roll(int field, boolean up) { roll(field, up ? 1 : -1); }
    public void roll(int field, int amount) { set(field, get(field) + amount); }
    
    @Override public boolean equals(Object obj) { return obj instanceof Calendar && getTimeInMillis() == ((Calendar)obj).getTimeInMillis(); }
    @Override public int hashCode() { return (int)(time ^ (time >>> 32)); }
    @Override public String toString() { return getClass().getName() + "[time=" + time + "]"; }
    @Override public int compareTo(Calendar anotherCalendar) { return Long.compare(getTimeInMillis(), anotherCalendar.getTimeInMillis()); }
    
    public Object clone() { try { return super.clone(); } catch (CloneNotSupportedException e) { throw new InternalError(e); } }
}