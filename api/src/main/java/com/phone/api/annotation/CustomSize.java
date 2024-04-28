package com.phone.api.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomSize {
    String message() default "{custom.validation.constraints.size.message}";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

}
