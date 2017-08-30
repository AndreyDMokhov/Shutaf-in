package com.shutafin.processors.annotations.response;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionResponse {
    SessionResponseType value() default SessionResponseType.NEW_SESSION;
}
