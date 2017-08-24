package com.shutafin.configuration;

import com.shutafin.processors.AuthenticationAnnotationsBeanPostProcessor;
<<<<<<<<< Temporary merge branch 1
import com.shutafin.processors.TraceLogBeanPostProcessor;
=========
import com.shutafin.processors.SessionResponseAnnotationBeanPostProcessor;
>>>>>>>>> Temporary merge branch 2
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan(basePackages = "com.shutafin")
@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:environment.config.properties"),

})
@EnableWebMvc
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
<<<<<<<<< Temporary merge branch 1
    public TraceLogBeanPostProcessor traceLogBeanPostProcessor() {
        return new TraceLogBeanPostProcessor();
=========
    public SessionResponseAnnotationBeanPostProcessor sessionAnnotationsResponseBeanPostProcessor(){
        return new SessionResponseAnnotationBeanPostProcessor();
>>>>>>>>> Temporary merge branch 2
    }

}
