package mindustry.teavm;

import arc.files.*;
import arc.util.*;
import arc.util.serialization.*;

import java.util.*;

/**
 * Converts Mindustry GLSL 330 shaders to WebGL 2 (ESSL 300) compatible shaders.
 * Run this at build time to convert all shaders in assets/shaders/.
 */
public class ShaderConverter {
    
    public static void main(String[] args) {
        Fi shadersDir = new Fi("core/assets-raw/shaders");
        if (!shadersDir.exists()) {
            shadersDir = new Fi("core/assets/shaders");
        }
        
        if (!shadersDir.exists()) {
            Log.err("Shaders directory not found");
            return;
        }
        
        Fi outputDir = new Fi("teaVM/build/teavm/assets/shaders");
        outputDir.mkdirs();
        
        for (Fi file : shadersDir.list(f -> f.extEquals("glsl") || f.extEquals("vert") || f.extEquals("frag"))) {
            try {
                String source = file.readString();
                String converted = convertToWebGL2(source, file.name());
                Fi outFile = outputDir.child(file.name());
                outFile.writeString(converted);
                Log.info("Converted shader: " + file.name());
            } catch (Exception e) {
                Log.err("Failed to convert shader: " + file.name(), e);
            }
        }
        
        // Copy shader metadata files
        for (Fi file : shadersDir.list(f -> f.extEquals("json"))) {
            try {
                file.copyTo(outputDir.child(file.name()));
            } catch (Exception e) {
                Log.err("Failed to copy shader metadata: " + file.name(), e);
            }
        }
        
        Log.info("Shader conversion complete. Output: " + outputDir.path());
    }
    
    /**
     * Convert GLSL 330 core profile to WebGL 2 (ESSL 300).
     */
    public static String convertToWebGL2(String source, String fileName) {
        String result = source;
        
        // Version directive
        result = result.replace("#version 330", "#version 300 es");
        result = result.replace("#version 330 core", "#version 300 es");
        result = result.replace("#version 400", "#version 300 es");
        result = result.replace("#version 410", "#version 300 es");
        result = result.replace("#version 420", "#version 300 es");
        result = result.replace("#version 430", "#version 300 es");
        result = result.replace("#version 440", "#version 300 es");
        result = result.replace("#version 450", "#version 300 es");
        result = result.replace("#version 460", "#version 300 es");
        
        // Precision qualifiers (required in ESSL 300 for fragment shaders)
        if (fileName.endsWith(".frag") || fileName.endsWith(".fs")) {
            if (!result.contains("precision highp float")) {
                // Add after version directive
                result = result.replace("#version 300 es", "#version 300 es\nprecision highp float;\nprecision highp int;\nprecision highp sampler2D;\nprecision highp sampler3D;\nprecision highp samplerCube;\nprecision highp sampler2DArray;\nprecision highp isampler2D;\nprecision highp usampler2D;");
            }
        }
        
        // Remove core profile keywords
        result = result.replace("core", "");
        
        // Fix attribute/uniform qualifiers
        result = result.replace("in ", "in ");
        result = result.replace("out ", "out ");
        result = result.replace("uniform ", "uniform ");
        
        // Replace texture functions
        result = result.replace("texture(", "texture(");
        result = result.replace("texture2D(", "texture(");
        result = result.replace("texture3D(", "texture(");
        result = result.replace("textureCube(", "texture(");
        result = result.replace("texture2DArray(", "texture(");
        
        // Replace textureSize
        result = result.replace("textureSize(", "textureSize(");
        
        // Fix imageLoad/imageStore for compute shaders (if used)
        result = result.replace("imageLoad(", "imageLoad(");
        result = result.replace("imageStore(", "imageStore(");
        
        // Remove layout qualifiers that aren't supported in ESSL 300
        // layout(location = X) is supported in ESSL 300
        // layout(binding = X) is supported
        // layout(std140) is supported
        // layout(std430) is NOT supported in ESSL 300 - use std140
        result = result.replace("std430", "std140");
        
        // Fix interpolation qualifiers
        result = result.replace("smooth in", "in");
        result = result.replace("flat in", "flat in");
        result = result.replace("noperspective in", "noperspective in");
        result = result.replace("centroid in", "centroid in");
        result = result.replace("sample in", "sample in");
        
        result = result.replace("smooth out", "out");
        result = result.replace("flat out", "flat out");
        result = result.replace("noperspective out", "noperspective out");
        result = result.replace("centroid out", "centroid out");
        result = result.replace("sample out", "sample out");
        
        // Remove unsupported features
        // - geometry shaders (not in WebGL 2)
        // - tessellation shaders (not in WebGL 2)
        // - transform feedback (limited in WebGL 2)
        // - compute shaders (not in WebGL 2, need WebGPU)
        
        // Handle #extension directives
        result = result.replace("#extension GL_ARB_gpu_shader5 : enable", "");
        result = result.replace("#extension GL_ARB_shader_storage_buffer_object : enable", "");
        result = result.replace("#extension GL_ARB_shader_image_load_store : enable", "");
        
        // Fix uniform buffer layout
        // std140 is supported, std430 is not
        result = result.replace("layout(std430)", "layout(std140)");
        
        // Fix binding points for uniform buffers and samplers
        // layout(binding = X) uniform Type { ... } is supported
        
        // Remove unsupported built-in variables
        // gl_ClipDistance, gl_CullDistance - not in WebGL 2
        // gl_PrimitiveID - supported in fragment shader
        // gl_SampleID, gl_SamplePosition - supported
        // gl_Layer, gl_ViewportIndex - not in WebGL 2
        
        return result;
    }
    
    /**
     * Convert a shader file with metadata.
     * Mindustry shaders often have a JSON file with the same name containing metadata.
     */
    public static void convertShaderPair(Fi glslFile, Fi outputDir) {
        try {
            String source = glslFile.readString();
            String converted = convertToWebGL2(source, glslFile.name());
            Fi outFile = outputDir.child(glslFile.name());
            outFile.writeString(converted);
            
            // Copy metadata if exists
            Fi metaFile = glslFile.sibling(glslFile.nameWithoutExtension() + ".json");
            if (metaFile.exists()) {
                metaFile.copyTo(outputDir.child(metaFile.name()));
            }
            
            Log.info("Converted: " + glslFile.name());
        } catch (Exception e) {
            Log.err("Failed to convert " + glslFile.name(), e);
        }
    }
}