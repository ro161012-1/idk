package mindustry.html.js;

public class GwtClipboard {
    
    public static native void writeText(String text) /*-{
        if ($wnd.navigator.clipboard && $wnd.navigator.clipboard.writeText) {
            $wnd.navigator.clipboard.writeText(text).catch(function(err) {
                console.warn('Clipboard write failed:', err);
            });
        } else {
            // Fallback for older browsers
            var textArea = $doc.createElement('textarea');
            textArea.value = text;
            textArea.style.position = 'fixed';
            textArea.style.left = '-999999px';
            textArea.style.top = '-999999px';
            $doc.body.appendChild(textArea);
            textArea.focus();
            textArea.select();
            try {
                $doc.execCommand('copy');
            } catch (err) {
                console.warn('Clipboard copy failed:', err);
            }
            $doc.body.removeChild(textArea);
        }
    }-*/;
    
    public static native String readText() /*-{
        if ($wnd.navigator.clipboard && $wnd.navigator.clipboard.readText) {
            // Note: This is async in real API, but we can't do async in JSNI easily
            // Return empty string for now - would need callback-based approach
            return "";
        }
        return "";
    }-*/;
    
    public static native boolean canRead() /*-{
        return !!($wnd.navigator.clipboard && $wnd.navigator.clipboard.readText);
    }-*/;
}