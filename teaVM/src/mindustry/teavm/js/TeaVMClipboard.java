package mindustry.teavm.js;

import org.teavm.jso.*;
import org.teavm.jso.dom.html.*;

public class TeaVMClipboard {
    
    public static native void writeText(String text) /*-{
        if ($wnd.navigator.clipboard && $wnd.navigator.clipboard.writeText) {
            $wnd.navigator.clipboard.writeText(text).catch(function(err) {
                console.warn('Clipboard write failed:', err);
                // Fallback
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
            // Note: This is async in real API
            // For sync compatibility, return empty and use callback-based approach
            return "";
        }
        return "";
    }-*/;
    
    public static void readTextAsync(Callback<String> callback) {
        // Async version for proper clipboard reading
        nativeReadTextAsync(callback);
    }
    
    private static native void nativeReadTextAsync(Callback<String> callback) /*-{
        if ($wnd.navigator.clipboard && $wnd.navigator.clipboard.readText) {
            $wnd.navigator.clipboard.readText().then(function(text) {
                callback.@mindustry.teavm.js.Callback::onResult(Ljava/lang/Object;)(text);
            }).catch(function(err) {
                console.warn('Clipboard read failed:', err);
                callback.@mindustry.teavm.js.Callback::onResult(Ljava/lang/Object;)("");
            });
        } else {
            callback.@mindustry.teavm.js.Callback::onResult(Ljava/lang/Object;)("");
        }
    }-*/;
    
    public static native boolean canRead() /*-{
        return !!($wnd.navigator.clipboard && $wnd.navigator.clipboard.readText);
    }-*/;
    
    @FunctionalInterface
    public interface Callback<T> {
        void onResult(T result);
    }
}