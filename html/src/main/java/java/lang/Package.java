package java.lang;

public class Package implements AnnotatedElement {
    private final String name;
    
    public Package(String name) { this.name = name; }
    public String getName() { return name; }
    public String getSpecificationTitle() { return null; }
    public String getSpecificationVersion() { return null; }
    public String getSpecificationVendor() { return null; }
    public String getImplementationTitle() { return null; }
    public String getImplementationVersion() { return null; }
    public String getImplementationVendor() { return null; }
    public boolean isSealed() { return false; }
    public boolean isSealed(URL url) { return false; }
    public URL getSealBase() { return null; }
    public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> annotationClass) { return null; }
    public java.lang.annotation.Annotation[] getAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
    public String toString() { return "package " + name; }
    public int hashCode() { return name.hashCode(); }
    public boolean equals(Object obj) { return obj instanceof Package && name.equals(((Package)obj).name); }
}
