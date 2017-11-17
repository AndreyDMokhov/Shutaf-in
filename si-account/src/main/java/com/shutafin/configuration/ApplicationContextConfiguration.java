package com.shutafin.configuration;

import com.shutafin.processors.AuthenticationAnnotationsBeanPostProcessor;
import com.shutafin.processors.SessionResponseAnnotationBeanPostProcessor;
import com.shutafin.processors.TraceLogBeanPostProcessor;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
public class ApplicationContextConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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