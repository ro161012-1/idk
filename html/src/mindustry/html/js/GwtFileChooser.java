package mindustry.html.js;

import arc.files.*;
import mindustry.ui.FileChooser.*;
import elemental2.dom.*;
import elemental2.core.*;
import jsinterop.annotations.*;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class GwtFileChooser {
    
    @JsMethod(name = "showFileChooser")
    public static native void show(FileChooserParams params);
    
    // Static initializer to set up the JS function
    static {
        setupFileChooser();
    }
    
    @JsMethod(namespace = JsPackage.GLOBAL)
    private static native void setupFileChooser();
    
    /*
    JavaScript implementation (included in index.html):
    
    function showFileChooser(params) {
        const input = document.createElement('input');
        input.type = 'file';
        input.multiple = params.allowMultiple;
        input.accept = params.extensions.map(ext => '.' + ext).join(',');
        
        input.onchange = (e) => {
            const files = Array.from(e.target.files);
            if (files.length === 0) return;
            
            // Read files as ArrayBuffer
            const promises = files.map(file => {
                return new Promise((resolve) => {
                    const reader = new FileReader();
                    reader.onload = () => resolve({
                        name: file.name,
                        data: reader.result
                    });
                    reader.readAsArrayBuffer(file);
                });
            });
            
            Promise.all(promises).then(results => {
                // Call back into Java
                const javaFiles = results.map(r => {
                    // Create a Java Fi object equivalent
                    return {
                        name: r.name,
                        bytes: new Uint8Array(r.data)
                    };
                });
                
                // Use JSNI to call Java callback
                if (params.handleChooseResult) {
                    params.handleChooseResult(javaFiles);
                }
            });
        };
        
        input.click();
    }
    */
}