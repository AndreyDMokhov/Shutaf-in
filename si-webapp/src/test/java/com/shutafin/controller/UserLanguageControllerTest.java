package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.service.UserLanguageService;
import com.shutafin.system.BaseTestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
public class UserLanguageControllerTest extends BaseTestImpl  {

    private static final String REGISTRATION_REQUEST_URL = "/user/account/language";

    @Autowired
    private UserLanguageService userLanguageService;

    @Before
    public void setUp() {
        Mockito.doNothing().when(userLanguageService).updateUserLanguage(Mockito.any(UserLanguageWeb.class), Mockito.any(User.class));
    }

    @Test
    public void userLanguageController_Positive(){
        String bodyJSON = "{\"id\":\"1\"}";
        List<HttpHeaders> headers = addSessionIdToHeader("40042cd8-51d0-4282-b431-36ee7f6dcaef");

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                bodyJSON,
                HttpMethod.PUT,
                headers);

        Assert.assertNull(response.getError());
    }


    @Test
    public void userLanguageController_NoIdNull(){
        String bodyJSON = "{\"id\":\"0\"}";
        List<HttpHeaders> headers = addSessionIdToHeader("40042cd8-51d0-4282-b431-36ee7f6dcaef");

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                bodyJSON,
                HttpMethod.PUT,
                headers);

        Assert.assertNotNull(response.getError());
//        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.INPUT.getErrorCodeType());
//        Assert.assertEquals(response.getError().getErrorMessage(), "");
    }


    public List<HttpHeaders> addSessionIdToHeader(String sessionId){
        List<HttpHeaders> headers = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("session_id", sessionId);
        headers.add(httpHeaders);
        return headers;
    }
}

//        Assert.assertNotNull(response.getError());
//        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());