package mindustry.html.js;

public class GwtWindow {
    
    public static native void open(String url, String target, String features) /*-{
        $wnd.open(url, target, features);
    }-*/;
    
    public static native void alert(String message) /*-{
        $wnd.alert(message);
    }-*/;
    
    public static native boolean confirm(String message) /*-{
        return $wnd.confirm(message);
    }-*/;
    
    public static native String prompt(String message, String defaultValue) /*-{
        return $wnd.prompt(message, defaultValue);
    }-*/;
}