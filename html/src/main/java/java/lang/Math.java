package java.lang;

public final class Math {
    public static final double E = 2.718281828459045;
    public static final double PI = 3.141592653589793;
    
    public static int abs(int a) { return a < 0 ? -a : a; }
    public static long abs(long a) { return a < 0 ? -a : a; }
    public static float abs(float a) { return a < 0 ? -a : a; }
    public static double abs(double a) { return a < 0 ? -a : a; }
    
    public static int max(int a, int b) { return a > b ? a : b; }
    public static long max(long a, long b) { return a > b ? a : b; }
    public static float max(float a, float b) { return a > b ? a : b; }
    public static double max(double a, double b) { return a > b ? a : b; }
    
    public static int min(int a, int b) { return a < b ? a : b; }
    public static long min(long a, long b) { return a < b ? a : b; }
    public static float min(float a, float b) { return a < b ? a : b; }
    public static double min(double a, double b) { return a < b ? a : b; }
    
    public static double sin(double a) { return java.lang.Math.sin(a); }
    public static double cos(double a) { return java.lang.Math.cos(a); }
    public static double tan(double a) { return java.lang.Math.tan(a); }
    public static double asin(double a) { return java.lang.Math.asin(a); }
    public static double acos(double a) { return java.lang.Math.acos(a); }
    public static double atan(double a) { return java.lang.Math.atan(a); }
    public static double atan2(double y, double x) { return java.lang.Math.atan2(y, x); }
    public static double sqrt(double a) { return java.lang.Math.sqrt(a); }
    public static double cbrt(double a) { return java.lang.Math.cbrt(a); }
    public static double ceil(double a) { return java.lang.Math.ceil(a); }
    public static double floor(double a) { return java.lang.Math.floor(a); }
    public static double rint(double a) { return java.lang.Math.rint(a); }
    public static int round(float a) { return (int)java.lang.Math.round(a); }
    public static long round(double a) { return java.lang.Math.round(a); }
    public static double pow(double a, double b) { return java.lang.Math.pow(a, b); }
    public static double exp(double a) { return java.lang.Math.exp(a); }
    public static double log(double a) { return java.lang.Math.log(a); }
    public static double log10(double a) { return java.lang.Math.log10(a); }
    public static double log1p(double a) { return java.lang.Math.log1p(a); }
    public static double expm1(double a) { return java.lang.Math.expm1(a); }
    public static double toDegrees(double angrad) { return angrad * 180.0 / PI; }
    public static double toRadians(double angdeg) { return angdeg * PI / 180.0; }
    public static double random() { return java.lang.Math.random(); }
    public static int signum(int a) { return (a >> 31) | (-a >>> 31); }
    public static long signum(long a) { return (a >> 63) | (-a >>> 63); }
    public static float signum(float a) { return a == 0 ? 0 : a < 0 ? -1 : 1; }
    public static double signum(double a) { return a == 0 ? 0 : a < 0 ? -1 : 1; }
    public static double ulp(double d) { return java.lang.Math.ulp(d); }
    public static float ulp(float f) { return java.lang.Math.ulp(f); }
    public static double sinh(double x) { return java.lang.Math.sinh(x); }
    public static double cosh(double x) { return java.lang.Math.cosh(x); }
    public static double tanh(double x) { return java.lang.Math.tanh(x); }
    public static double hypot(double x, double y) { return java.lang.Math.hypot(x, y); }
    public static int floorDiv(int x, int y) { return (int)java.lang.Math.floorDiv(x, y); }
    public static long floorDiv(long x, long y) { return java.lang.Math.floorDiv(x, y); }
    public static int floorMod(int x, int y) { return (int)java.lang.Math.floorMod(x, y); }
    public static long floorMod(long x, long y) { return java.lang.Math.floorMod(x, y); }
}
