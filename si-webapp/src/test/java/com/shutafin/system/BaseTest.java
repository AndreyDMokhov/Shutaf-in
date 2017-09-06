package com.shutafin.system;

import com.shutafin.model.web.APIWebResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

/**
 * Created by Edward Kats on 7/27/2017.
 */
public interface BaseTest {

    APIWebResponse getResponse(ControllerRequest request);

    MockHttpServletResponse getServletResponse(ControllerRequest request);

    MvcResult getMvcResult(ControllerRequest request);

    APIWebResponse getResponse(MockHttpServletResponse mockHttpServletResponse);

    void getAssertInputValidationError(String requestUrl, String jsonContent,
                                       List<String> errorList);

    void getAssertInputValidationError(String requestUrl, String jsonContent,
                                       List<String> errorList, List<HttpHeaders> sessionHeaders);

    void getAssertInputValidationError(String requestUrl, Object requestObject,
                                       List<String> errorList);

    void getAssertInputValidationError(String requestUrl, Object requestObject,
                                       List<String> errorList, List<HttpHeaders> sessionHeaders);
}
