package com.shutafin.controller;

import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.service.LogoutService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.system.BaseTestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LogoutControllerTest extends BaseTestImpl{

    public static final String LOGOUT_REQUEST_URL = "/logout/";

    @MockBean
    private LogoutService logoutService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp(){
        Mockito.doNothing().when(logoutService).logout(any(UserSession.class));
        Mockito.when(sessionManagementService.findValidUserSession(anyString())).thenReturn(new UserSession());
    }

    @Test
    public void LogoutRequest_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader("40042cd8-51d0-4282-b431-36ee7f6dcaef");
        APIWebResponse response = getResponse(LOGOUT_REQUEST_URL, HttpMethod.POST, headers);
        Assert.assertNull(response.getError());
    }

    @Test
    public void LogoutRequest_HeaderNull(){
        APIWebResponse response = getResponse(LOGOUT_REQUEST_URL, HttpMethod.POST);
        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }


    public List<HttpHeaders> addSessionIdToHeader(String sessionId){
        List<HttpHeaders> headers = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("session_id", sessionId);
        headers.add(httpHeaders);
        return headers;
    }
}
