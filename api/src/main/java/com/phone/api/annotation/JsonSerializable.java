package com.phone.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Çalışma zamanında korunması gerektiğini belirtmektedir.
@Target(ElementType.TYPE) // Hangi tipler üzerinde kullanılabileceğini belirtmektedir.
public @interface JsonSerializable{

}
