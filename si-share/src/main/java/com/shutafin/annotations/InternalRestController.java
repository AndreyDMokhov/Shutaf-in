package com.shutafin.annotations;


import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
public @interface InternalRestController {

    @AliasFor(annotation = RestController.class, attribute = "value")
    String value() default "";
}
