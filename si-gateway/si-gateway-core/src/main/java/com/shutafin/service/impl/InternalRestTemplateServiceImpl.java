package com.shutafin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.service.InternalRestTemplateService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class InternalRestTemplateServiceImpl implements InternalRestTemplateService {

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getResponse(HttpMethod httpMethod, String url, Map<String, ? extends Serializable> uriVariables,
                                                  Object requestObject, Class<T> returnType) {
        HttpEntity<String> request = createRequestEntity(requestObject);
        if (uriVariables != null) {
            return restTemplate.exchange(url, httpMethod, request, returnType, uriVariables).getBody();
        } else {
            return restTemplate.exchange(url, httpMethod, request, returnType).getBody();
        }
    }

    @Override
    public List<?> getListResponse(HttpMethod httpMethod, String url, Map<String, ? extends Serializable> uriVariables,
                                   Object requestObject, Class returnType) {
        HttpEntity<String> request = createRequestEntity(requestObject);
        return restTemplate.exchange(url, httpMethod, request, new ParameterizedTypeReference<List<?>>() {
        }, uriVariables).getBody();
    }

    private HttpEntity<String> createRequestEntity(Object obj) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            if (obj != null) {
                String json = mapper.writeValueAsString(obj);
                HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
                return requestEntity;
            }
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            return requestEntity;
        } catch (JsonProcessingException exp) {
            exp.printStackTrace();
            return null;
        }
    }
}
