package com.shutafin.configuration;

import com.shutafin.annotations.InternalRestController;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;


@Configuration
public class WebContextConfiguration extends WebMvcConfigurerAdapter {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customMappingJackson2HttpMessageConverter());
    }

    @Bean
    public CustomMappingJackson2HttpMessageConverter customMappingJackson2HttpMessageConverter() {
        return new CustomMappingJackson2HttpMessageConverter();
    }

    @Bean
    public WebMvcRegistrationsAdapter webMvcRegistrationsHandlerMapping() {
        return new WebMvcRegistrationsAdapter() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    private final static String EXTERNAL_API_BASE_PATH = "api";
                    private final static String INTERNAL_API_BASE_PATH = "internal";

                    @Override
                    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
                        Class<?> beanType = method.getDeclaringClass();

                        boolean isExternal = AnnotationUtils.findAnnotation(beanType, RestController.class) != null;

                        boolean isInternal = AnnotationUtils.findAnnotation(beanType, InternalRestController.class) != null;
                        String apiPrefix = null;

                        if (isExternal) {
                            apiPrefix = EXTERNAL_API_BASE_PATH;
                        }

                        if (isInternal) {
                            apiPrefix = INTERNAL_API_BASE_PATH;
                        }



                        if (isExternal || isInternal) {
                            PatternsRequestCondition apiPattern = new PatternsRequestCondition(apiPrefix);
                            apiPattern = apiPattern.combine(mapping.getPatternsCondition());

                            mapping = new RequestMappingInfo(
                                    mapping.getName(),
                                    apiPattern,
                                    mapping.getMethodsCondition(),
                                    mapping.getParamsCondition(),
                                    mapping.getHeadersCondition(),
                                    mapping.getConsumesCondition(),
                                    mapping.getProducesCondition(),
                                    mapping.getCustomCondition());
                        }


                        super.registerHandlerMethod(handler, method, mapping);
                    }
                };
            }
        };
    }

    @Bean
    public ClientSessionInboundChannelInterceptor clientSessionInboundChannelInterceptor(){
        return new ClientSessionInboundChannelInterceptor();
    }

    @Bean
    public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
        return new HttpSessionIdHandshakeInterceptor();
    }

}
