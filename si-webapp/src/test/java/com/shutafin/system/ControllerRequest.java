package com.shutafin.system;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;

public class TestRequest {

    private final String url;
    private final String jsonContent;
    private final Object requestObject;
    private final HttpMethod httpMethod;
    private final List<HttpHeaders> headers;
    private final Class responseClass;


    public TestRequest(TestRequestBuilder builder) {
        this.url = builder.url;
        this.jsonContent = builder.jsonContent;
        this.requestObject = builder.requestObject;
        this.httpMethod = builder.httpMethod;
        this.headers = builder.headers;
        this.responseClass = builder.responseClass;
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

    public static TestRequestBuilder builder() {
        return new TestRequestBuilder();
    }

    public static class TestRequestBuilder {

        private String url = null;
        private String jsonContent = "";
        private Object requestObject = null;
        private HttpMethod httpMethod = null;
        private List<HttpHeaders> headers = null;
        private Class responseClass = null;

        public TestRequest build() {
            return new TestRequest(this);
        }

        public TestRequestBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public TestRequestBuilder setJsonContext(String jsonContent) {
            this.jsonContent = jsonContent;
            return this;
        }

        public TestRequestBuilder setRequestObject(Object requestObject) {
            this.requestObject = requestObject;
            return this;
        }

        public TestRequestBuilder setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public TestRequestBuilder setHeaders(List<HttpHeaders> headers) {
            this.headers = headers;
            return this;
        }

        public TestRequestBuilder setResponseClass(Class responseClass) {
            this.responseClass = responseClass;
            return this;
        }
    }
}
