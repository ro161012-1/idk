package java.lang;

public interface AnnotatedElement {
    <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> annotationClass);
    java.lang.annotation.Annotation[] getAnnotations();
    java.lang.annotation.Annotation[] getDeclaredAnnotations();
    boolean isAnnotationPresent(Class<? extends java.lang.annotation.Annotation> annotationClass);
}
