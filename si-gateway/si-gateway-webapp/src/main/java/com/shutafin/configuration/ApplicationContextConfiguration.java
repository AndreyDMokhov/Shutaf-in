package com.shutafin.configuration;

import com.shutafin.processors.AuthenticationAnnotationsBeanPostProcessor;
import com.shutafin.processors.SessionResponseAnnotationBeanPostProcessor;
import com.shutafin.processors.TraceLogBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "classpath:server.properties")
public class ApplicationContextConfiguration {


    @Bean
    public AuthenticationAnnotationsBeanPostProcessor authenticationHandlerArgumentResolverProcessor() {
        return new AuthenticationAnnotationsBeanPostProcessor();
    }

    @Bean
    public SessionResponseAnnotationBeanPostProcessor sessionAnnotationsResponseBeanPostProcessor(){
        return new SessionResponseAnnotationBeanPostProcessor();
    }

    @Bean
    public TraceLogBeanPostProcessor traceLogBeanPostProcessor() {
        return new TraceLogBeanPostProcessor();
    }

}