package mindustry.html.js;

public class GwtLocalStorage {
    
    public static native String getItem(String key) /*-{
        return $wnd.localStorage.getItem(key);
    }-*/;
    
    public static native void setItem(String key, String value) /*-{
        $wnd.localStorage.setItem(key, value);
    }-*/;
    
    public static native void removeItem(String key) /*-{
        $wnd.localStorage.removeItem(key);
    }-*/;
    
    public static native void clear() /*-{
        $wnd.localStorage.clear();
    }-*/;
}