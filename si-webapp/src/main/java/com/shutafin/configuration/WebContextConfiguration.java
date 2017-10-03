package com.shutafin.configuration;

import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;


@Configuration
public class WebContextConfiguration extends WebMvcConfigurerAdapter {

    private static final String API_PREFIX = "api";


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customMappingJackson2HttpMessageConverter());
    }

    @Bean
    public CustomMappingJackson2HttpMessageConverter customMappingJackson2HttpMessageConverter() {
        return new CustomMappingJackson2HttpMessageConverter();
    }

    @Bean
    public WebMvcRegistrationsAdapter restPrefixAppender() {
        return new WebMvcRegistrationsAdapter() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    @Override
                    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
                        RequestMappingInfo mappingForMethod = super.getMappingForMethod(method, handlerType);
                        if (mappingForMethod == null) {
                            return null;
                        }
                        return RequestMappingInfo.paths(API_PREFIX).build().combine(mappingForMethod);
                    }
                };
            }
        };
    }

    @Bean
    public ClientSessionInboundChannelInterceptor clientSessionInboundChannelInterceptor(){
        return new ClientSessionInboundChannelInterceptor();
    }
}
