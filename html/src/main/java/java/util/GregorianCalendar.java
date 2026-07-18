package java.util;

public class GregorianCalendar extends Calendar {
    public static final int BC = 0;
    public static final int AD = 1;
    
    private static final long serialVersionUID = -8125100834729963327L;
    
    public GregorianCalendar() { this(TimeZone.getDefault(), Locale.getDefault()); }
    public GregorianCalendar(TimeZone zone) { this(zone, Locale.getDefault()); }
    public GregorianCalendar(Locale aLocale) { this(TimeZone.getDefault(), aLocale); }
    public GregorianCalendar(TimeZone zone, Locale aLocale) { super(zone, aLocale); }
    public GregorianCalendar(int year, int month, int dayOfMonth) { this(year, month, dayOfMonth, 0, 0, 0); }
    public GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) { this(year, month, dayOfMonth, hourOfDay, minute, 0); }
    public GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        super(TimeZone.getDefault(), Locale.getDefault());
        set(YEAR, year);
        set(MONTH, month);
        set(DAY_OF_MONTH, dayOfMonth);
        set(HOUR_OF_DAY, hourOfDay);
        set(MINUTE, minute);
        set(SECOND, second);
    }
    
    @Override protected void computeFields() {
        // Simplified - in real implementation would compute from time
        if (time == 0) return;
        java.util.Date date = new java.util.Date(time);
        set(YEAR, date.getYear() + 1900);
        set(MONTH, date.getMonth());
        set(DAY_OF_MONTH, date.getDate());
        set(HOUR_OF_DAY, date.getHours());
        set(MINUTE, date.getMinutes());
        set(SECOND, date.getSeconds());
        set(MILLISECOND, 0);
        areFieldsSet = true;
    }
    
    @Override protected void computeTime() {
        // Simplified - in real implementation would compute time from fields
        int year = get(YEAR);
        int month = get(MONTH);
        int day = get(DAY_OF_MONTH);
        int hour = get(HOUR_OF_DAY);
        int minute = get(MINUTE);
        int second = get(SECOND);
        time = java.util.Date.UTC(year - 1900, month, day, hour, minute, second);
        isTimeSet = true;
    }
    
    @Override public int getMinimum(int field) {
        switch (field) {
            case ERA: return BC;
            case YEAR: return 1;
            case MONTH: return JANUARY;
            case DAY_OF_MONTH: return 1;
            case HOUR_OF_DAY: return 0;
            case MINUTE: return 0;
            case SECOND: return 0;
            case MILLISECOND: return 0;
            default: return 0;
        }
    }
    
    @Override public int getMaximum(int field) {
        switch (field) {
            case ERA: return AD;
            case YEAR: return 9999;
            case MONTH: return DECEMBER;
            case DAY_OF_MONTH: return 31;
            case HOUR_OF_DAY: return 23;
            case MINUTE: return 59;
            case SECOND: return 59;
            case MILLISECOND: return 999;
            default: return 0;
        }
    }
    
    @Override public int getGreatestMinimum(int field) { return getMinimum(field); }
    @Override public int getLeastMaximum(int field) { return getMaximum(field); }
    
    public boolean isLeapYear(int year) { return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0); }
}