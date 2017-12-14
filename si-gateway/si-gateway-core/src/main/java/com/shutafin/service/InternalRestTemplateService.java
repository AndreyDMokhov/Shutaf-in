package com.shutafin.service;

import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface InternalRestTemplateService {
    <T> T getResponse(HttpMethod httpMethod, String url, Map<String, ? extends Serializable> uriVariables,
                                           Object requestObject, Class<T> returnType);

    List<?> getListResponse(HttpMethod httpMethod, String url, Map<String, ? extends Serializable> uriVariables,
                            Object requestObject, Class returnType);
}
