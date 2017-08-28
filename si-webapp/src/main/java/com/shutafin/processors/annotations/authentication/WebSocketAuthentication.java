package com.shutafin.processors.annotations.authentication;

import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSocketAuthentication {


}


