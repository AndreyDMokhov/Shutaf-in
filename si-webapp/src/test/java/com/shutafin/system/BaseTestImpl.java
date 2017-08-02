package com.shutafin.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shutafin.App;
import com.shutafin.configuration.*;
import com.shutafin.controller.APIWebResponseDeserializer;
import com.shutafin.model.web.APIWebResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(value = App.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = {
        ApplicationContextConfiguration.class,
        DatabaseConnectivityContextConfiguration.class,
        MessageConverterConfigurer.class,
        RoutingConfigurer.class,
        SMTPContextConfiguration.class
})
public class BaseTestImpl implements BaseTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(APIWebResponse.class, new APIWebResponseDeserializer())
            .create();

    @Autowired
    private MockMvc mockMvc;


    @Override
    public APIWebResponse getResponse(MvcResult mvcResult) {
        try {
            String json = mvcResult.getResponse().getContentAsString();
            APIWebResponse apiResponse = gson.fromJson(json, APIWebResponse.class);

            if (apiResponse == null) {
                apiResponse = new APIWebResponse();
            }

            return apiResponse;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public APIWebResponse getResponse(String url, HttpMethod httpMethod) {
        return getResponse(url, "", httpMethod, null);
    }

    @Override
    public APIWebResponse getResponse(String url, HttpMethod httpMethod, List<HttpHeaders> headers) {
        return getResponse(url, "", httpMethod, headers);
    }

    @Override
    public APIWebResponse getResponse(String url, String jsonContent, HttpMethod httpMethod) {
        return getResponse(url, jsonContent, httpMethod, null);
    }

    @Override
    @SneakyThrows
    public APIWebResponse getResponse(String url, String jsonContent, HttpMethod httpMethod, List<HttpHeaders> headers) {

        MockHttpServletRequestBuilder builder = getHttpMethodSenderType(url, httpMethod)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        if (jsonContent != null && !jsonContent.trim().isEmpty()) {
            builder.content(jsonContent);
        }

        if (headers != null && !headers.isEmpty()) {
            for (HttpHeaders httpHeaders : headers) {
                builder.headers(httpHeaders);
            }
        }

        MvcResult result = mockMvc
                .perform(builder)
                .andDo(print())
                .andReturn();

        return getResponse(result);
    }

    @Override
    public APIWebResponse getResponse(String url, Object object, HttpMethod httpMethod) {
        return getResponse(url, object, httpMethod, null);
    }

    @Override
    @SneakyThrows
    public APIWebResponse getResponse(String url, Object object, HttpMethod httpMethod, List<HttpHeaders> headers) {
        return getResponse(url, gson.toJson(object), httpMethod, headers);
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

        throw new IllegalArgumentException(String.format("HTTP method %s is not supported", httpMethod.name()));
    }


}