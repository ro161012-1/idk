package mindustry.html.js;

public class GwtNavigator {
    
    public static native void vibrate(int ms) /*-{
        if ($wnd.navigator.vibrate) {
            $wnd.navigator.vibrate(ms);
        }
    }-*/;
    
    public static native boolean isOnline() /*-{
        return $wnd.navigator.onLine;
    }-*/;
    
    public static native String getUserAgent() /*-{
        return $wnd.navigator.userAgent;
    }-*/;
    
    public static native String getLanguage() /*-{
        return $wnd.navigator.language || $wnd.navigator.userLanguage;
    }-*/;
}