package com.shutafin.configuration;

import com.shutafin.processors.AuthenticationAnnotationsBeanPostProcessor;
import com.shutafin.processors.ControllerResponseBeanPostProcessor;
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
    @DependsOn("authenticationHandlerArgumentResolverProcessor")
    public ControllerResponseBeanPostProcessor controllerResponseHandlerBeanPostProcessor(){
        return new ControllerResponseBeanPostProcessor();
    }

}
