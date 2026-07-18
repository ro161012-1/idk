package java.util.concurrent;

public enum TimeUnit {
    NANOSECONDS(1),
    MICROSECONDS(1000),
    MILLISECONDS(1000000),
    SECONDS(1000000000),
    MINUTES(60000000000L),
    HOURS(3600000000000L),
    DAYS(86400000000000L);
    
    private final long nanosPerUnit;
    
    TimeUnit(long nanosPerUnit) { this.nanosPerUnit = nanosPerUnit; }
    
    public long toNanos(long duration) { return duration * nanosPerUnit; }
    public long toMicros(long duration) { return duration * nanosPerUnit / 1000; }
    public long toMillis(long duration) { return duration * nanosPerUnit / 1000000; }
    public long toSeconds(long duration) { return duration * nanosPerUnit / 1000000000; }
    public long toMinutes(long duration) { return duration * nanosPerUnit / 60000000000L; }
    public long toHours(long duration) { return duration * nanosPerUnit / 3600000000000L; }
    public long toDays(long duration) { return duration * nanosPerUnit / 86400000000000L; }
    
    public long convert(long sourceDuration, TimeUnit sourceUnit) {
        return sourceUnit.toNanos(sourceDuration) / nanosPerUnit;
    }
    
    public void sleep(long timeout) throws InterruptedException { java.lang.Thread.sleep(timeout); }
    public void timedWait(Object obj, long timeout) throws InterruptedException { obj.wait(timeout); }
}