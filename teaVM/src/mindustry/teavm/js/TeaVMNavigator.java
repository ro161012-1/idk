package mindustry.teavm.js;

import org.teavm.jso.*;
import org.teavm.jso.dom.html.*;

public class TeaVMNavigator {
    
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
    
    public static native void share(String title, String text, String url) /*-{
        if ($wnd.navigator.share) {
            $wnd.navigator.share({title: title, text: text, url: url});
        }
    }-*/;
}