package com.shutafin.configuration;

import com.shutafin.handler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateFactory implements FactoryBean<RestTemplate> {

    @Override
    public RestTemplate getObject() {
        RestTemplate restTemplate;
        return restTemplate = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
