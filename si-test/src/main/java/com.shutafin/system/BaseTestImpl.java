package com.shutafin.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shutafin.model.error.errors.InputValidationError;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.repository.base.BaseRepositoryFactoryBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@WebMvcTest
@ComponentScan("com.shutafin")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCaching
@EnableJpaRepositories(basePackages = "com.shutafin", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class BaseTestImpl implements BaseTest {

    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @Override
    public APIWebResponse getResponse(ControllerRequest request) {
        MockHttpServletResponse mockHttpServletResponse = getServletResponse(request);
        return getResponse(mockHttpServletResponse);
    }

    @Override
    public MockHttpServletResponse getServletResponse(ControllerRequest request) {
        return getMvcResult(request).getResponse();
    }

    @SneakyThrows
    @Override
    public MvcResult getMvcResult(ControllerRequest request) {

        MockHttpServletRequestBuilder builder = getHttpMethodSenderType(request.getUrl(), request.getHttpMethod())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        Class responseClass = request.getResponseClass();
        gson = getGsonForResponseClass(responseClass);

        String jsonContent = request.getJsonContent();
        Object requestObject = request.getRequestObject();
        if (requestObject != null) {
            jsonContent = gson.toJson(requestObject);
        }
        if (jsonContent != null && !jsonContent.trim().isEmpty()) {
            builder.content(jsonContent);
        }

        List<HttpHeaders> headers = request.getHeaders();
        if (headers != null && !CollectionUtils.isEmpty(headers)) {
            for (HttpHeaders httpHeaders : headers) {
                builder.headers(httpHeaders);
            }
        }

        return mockMvc
                .perform(builder)
                .andDo(print())
                .andReturn();
    }

    @Override
    public APIWebResponse getResponse(MockHttpServletResponse mockHttpServletResponse) {

        try {
            String json = mockHttpServletResponse.getContentAsString();
            APIWebResponse apiResponse = gson.fromJson(json, APIWebResponse.class);

            if (apiResponse == null) {
                apiResponse = new APIWebResponse();
            }

            return apiResponse;

        } catch (UnsupportedEncodingException e) {
            log.error("Illegal state exception. ", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void assertInputValidationError(String requestUrl, String jsonContent,
                                           List<String> errorList) {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(requestUrl)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(jsonContent)
                .build();
        assertErrorList(request, errorList);
    }

    @Override
    public void assertInputValidationError(String requestUrl, String jsonContent,
                                           List<String> errorList, List<HttpHeaders> sessionHeaders) {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(requestUrl)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(jsonContent)
                .setHeaders(sessionHeaders)
                .build();
        assertErrorList(request, errorList);
    }

    @Override
    public void assertInputValidationError(String requestUrl, Object requestObject,
                                           List<String> errorList) {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(requestUrl)
                .setHttpMethod(HttpMethod.POST)
                .setRequestObject(requestObject)
                .build();
        assertErrorList(request, errorList);
    }

    @Override
    public void assertInputValidationError(String requestUrl, Object requestObject,
                                           List<String> errorList, List<HttpHeaders> sessionHeaders) {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(requestUrl)
                .setHttpMethod(HttpMethod.POST)
                .setRequestObject(requestObject)
                .setHeaders(sessionHeaders)
                .build();
        assertErrorList(request, errorList);
    }

    private void assertErrorList(ControllerRequest request, List<String> errorList) {
        APIWebResponse response = getResponse(request);
        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }

    protected List<HttpHeaders> addSessionIdToHeader(String sessionId) {
        List<HttpHeaders> headers = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("session_id", sessionId);
        headers.add(httpHeaders);
        return headers;
    }

    private Gson getGsonForResponseClass(Class responseClass) {
        return new GsonBuilder()
                .registerTypeAdapter(APIWebResponse.class, new APIWebResponseDeserializer(responseClass))
                .create();
    }

    private MockHttpServletRequestBuilder getHttpMethodSenderType(String url, HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return MockMvcRequestBuilders.get(url);
            case PUT:
                return MockMvcRequestBuilders.put(url);
            case DELETE:
                return delete(url);
            case POST:
                return post(url);
        }

        log.error("HTTP method {} is not supported", httpMethod.name());
        throw new IllegalArgumentException(String.format("HTTP method %s is not supported", httpMethod.name()));
    }


}