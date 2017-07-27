package com.shutafin.system;

import com.shutafin.model.web.APIWebResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

/**
 * Created by Edward Kats on 7/27/2017.
 */
public interface BaseTest {

    APIWebResponse getResponse(MvcResult mvcResult);

    APIWebResponse getResponse(String url, HttpMethod httpMethod);
    APIWebResponse getResponse(String url, HttpMethod httpMethod, List<HttpHeaders> headers);

    APIWebResponse getResponse(String url, String jsonContent, HttpMethod httpMethod);
    APIWebResponse getResponse(String url, String jsonContent, HttpMethod httpMethod, List<HttpHeaders> headers);

    APIWebResponse getResponse(String url, Object object, HttpMethod httpMethod);
    APIWebResponse getResponse(String url, Object object, HttpMethod httpMethod, List<HttpHeaders> headers);

}
