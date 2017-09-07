package com.shutafin.system;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

public class ControllerRequest {
    private static final String API_PREFIX = "/api";

    private final String url;
    private final String jsonContent;
    private final Object requestObject;
    private final HttpMethod httpMethod;
    private final List<HttpHeaders> headers;
    private final Class responseClass;


    private ControllerRequest(ControllerRequestBuilder builder) {
        Assert.notNull(builder, "builder cannot be null");
        Assert.notNull(builder.url, "Request url cannot be null");

        if (builder.responseClass == null) {
            builder.responseClass = Void.class;
        }


        this.url = urlResolver(builder.url);
        this.jsonContent = builder.jsonContent;
        this.requestObject = builder.requestObject;
        this.httpMethod = builder.httpMethod;
        this.headers = builder.headers;
        this.responseClass = builder.responseClass;
    }

    private String urlResolver(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        if (url.startsWith("api") || url.startsWith("/api")) {
            return url;
        } else {
            return url.startsWith("/") ? API_PREFIX + url : API_PREFIX + "/" + url;
        }
    }


    public String getUrl() {
        return url;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public Object getRequestObject() {
        return requestObject;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public List<HttpHeaders> getHeaders() {
        return headers;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public static ControllerRequestBuilder builder() {
        return new ControllerRequestBuilder();
    }

public static class ControllerRequestBuilder {

    private String url = null;
    private String jsonContent = null;
    private Object requestObject = null;
    private HttpMethod httpMethod = null;
    private List<HttpHeaders> headers = null;
    private Class responseClass = null;

    public ControllerRequest build() {
        return new ControllerRequest(this);
    }

    public ControllerRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public ControllerRequestBuilder setJsonContext(String jsonContent) {
        this.jsonContent = jsonContent;
        return this;
    }

    public ControllerRequestBuilder setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    public ControllerRequestBuilder setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public ControllerRequestBuilder setHeaders(List<HttpHeaders> headers) {
        this.headers = headers;
        return this;
    }

    public ControllerRequestBuilder setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
        return this;
    }
}
}
