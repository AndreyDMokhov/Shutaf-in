package com.shutafin.configuration;

import com.shutafin.handler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateFactory implements FactoryBean<RestTemplate> {

    @Value("${service.user.name}")
    private String username;

    @Value("${service.user.password}")
    private String password;

    @Override
    public RestTemplate getObject() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .basicAuthorization(username, password)
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
