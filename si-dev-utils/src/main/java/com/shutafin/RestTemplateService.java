package com.shutafin;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {

    public void sendRequest(Object object, String url, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity;
        if (object == null) {
            entity = new HttpEntity<>(headers);
        } else {
            entity = new HttpEntity<>(new Gson().toJson(object), headers);
        }
        restTemplate.exchange(url, httpMethod, entity, Void.class);
    }

}