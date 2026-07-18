package java.util;

public final class Locale implements Serializable, Cloneable {
    private static final long serialVersionUID = 9149081749638150636L;
    
    public static final Locale ENGLISH = new Locale("en", "", "");
    public static final Locale US = new Locale("en", "US", "");
    public static final Locale UK = new Locale("en", "GB", "");
    public static final Locale FRANCE = new Locale("fr", "FR", "");
    public static final Locale GERMANY = new Locale("de", "DE", "");
    public static final Locale ITALY = new Locale("it", "IT", "");
    public static final Locale JAPAN = new Locale("ja", "JP", "");
    public static final Locale KOREA = new Locale("ko", "KR", "");
    public static final Locale CHINA = new Locale("zh", "CN", "");
    public static final Locale PRC = new Locale("zh", "CN", "");
    public static final Locale TAIWAN = new Locale("zh", "TW", "");
    public static final Locale CANADA = new Locale("en", "CA", "");
    public static final Locale CANADA_FRENCH = new Locale("fr", "CA", "");
    public static final Locale ROOT = new Locale("", "", "");
    
    private final String language;
    private final String country;
    private final String variant;
    
    public Locale(String language, String country) { this(language, country, ""); }
    public Locale(String language, String country, String variant) {
        this.language = (language == null ? "" : language).toLowerCase();
        this.country = (country == null ? "" : country).toUpperCase();
        this.variant = (variant == null ? "" : variant);
    }
    
    public String getLanguage() { return language; }
    public String getCountry() { return country; }
    public String getVariant() { return variant; }
    public String getDisplayLanguage() { return language; }
    public String getDisplayCountry() { return country; }
    public String getDisplayVariant() { return variant; }
    public String getDisplayName() { return language + "_" + country; }
    
    public static Locale getDefault() { return US; }
    public static void setDefault(Locale newLocale) {}
    public static Locale[] getAvailableLocales() { return new Locale[]{US, UK, FRANCE, GERMANY, ITALY, JAPAN, KOREA, CHINA, ROOT}; }
    
    @Override public String toString() { return language + "_" + country + (variant.isEmpty() ? "" : "_" + variant); }
    @Override public boolean equals(Object obj) { return obj instanceof Locale && language.equals(((Locale)obj).language) && country.equals(((Locale)obj).country) && variant.equals(((Locale)obj).variant); }
    @Override public int hashCode() { return language.hashCode() * 31 + country.hashCode() * 31 + variant.hashCode(); }
    @Override public Object clone() { return this; }
}