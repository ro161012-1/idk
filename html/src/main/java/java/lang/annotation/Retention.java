package java.lang.annotation;

import java.lang.annotation.RetentionPolicy;

public @interface Retention {
    RetentionPolicy value() default RetentionPolicy.RUNTIME;
}
