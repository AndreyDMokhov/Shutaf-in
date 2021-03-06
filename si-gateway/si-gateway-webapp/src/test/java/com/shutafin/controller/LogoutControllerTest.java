package com.shutafin.controller;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.service.LogoutService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.mockito.Mockito.anyString;

@RunWith(SpringJUnit4ClassRunner.class)
public class LogoutControllerTest extends BaseTestImpl {

    private static final String LOGOUT_REQUEST_URL = "/logout/";

    @MockBean
    private LogoutService logoutService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {
        Mockito.doNothing().when(logoutService).logout(anyString());
        Mockito.when(sessionManagementService.findUserWithValidSession(anyString())).thenReturn(1L);
    }

    @Test
    public void LogoutRequest_Positive() {
        List<HttpHeaders> headers = addSessionIdToHeader("40042cd8-51d0-4282-b431-36ee7f6dcaef");
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(LOGOUT_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setHeaders(headers)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNull(response.getError());
    }

    @Test
    public void LogoutRequest_HeaderNull() {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(LOGOUT_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }
}
