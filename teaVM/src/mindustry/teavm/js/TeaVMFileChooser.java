package mindustry.teavm.js;

import mindustry.ui.FileChooser.*;
import org.teavm.jso.*;
import org.teavm.jso.dom.html.*;

public class TeaVMFileChooser {
    
    public static void show(FileChooserParams params) {
        HTMLInputElement input = (HTMLInputElement) JSO.createElement("input");
        input.setType("file");
        input.setMultiple(params.allowMultiple);
        input.setAccept(String.join(",", params.extensions));
        
        input.addEventListener("change", event -> {
            HTMLInputElement target = (HTMLInputElement) event.getTarget();
            FileList files = target.getFiles();
            
            if (files.getLength() == 0) return;
            
            // Read files as ArrayBuffer
            int count = files.getLength();
            Fi[] resultFiles = new Fi[count];
            
            for (int i = 0; i < count; i++) {
                final int index = i;
                File file = files.getItem(i);
                FileReader reader = new FileReader();
                
                reader.addEventListener("load", e -> {
                    ArrayBuffer buffer = (ArrayBuffer) reader.getResult();
                    // Create Fi from ArrayBuffer
                    // This is a simplified version - real implementation would use IndexedDB
                    resultFiles[index] = new Fi(file.getName());
                    
                    // Check if all files loaded
                    boolean allLoaded = true;
                    for (Fi f : resultFiles) {
                        if (f == null) { allLoaded = false; break; }
                    }
                    
                    if (allLoaded) {
                        Core.app.post(() -> {
                            FileChooserDialog.setLastDirectory(resultFiles[0].isDirectory() ? resultFiles[0] : resultFiles[0].parent());
                            
                            if (!params.open) {
                                Fi single = resultFiles[0];
                                if (!Structs.contains(params.extensions, single::extEquals)) {
                                    single = single.parent().child(single.nameWithoutExtension() + "." + params.extensions[0]);
                                }
                                params.handleChooseResult(single);
                            } else {
                                params.handleChooseResult(resultFiles);
                            }
                        });
                    }
                });
                
                reader.readAsArrayBuffer(file);
            }
        });
        
        input.click();
    }
}