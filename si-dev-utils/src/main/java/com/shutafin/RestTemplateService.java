package com.shutafin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {

    public static final String USER_NAME = "root";
    public static final String USER_PASSWORD = "r00t";

    public void sendRequest(Object object, String url, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(USER_NAME, USER_PASSWORD));
        HttpEntity entity;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        if (object == null) {
            entity = new HttpEntity<>(headers);
        } else {
            entity = new HttpEntity<>(object, headers);
        }
        restTemplate.exchange(url, httpMethod, entity, Void.class);
    }

}
