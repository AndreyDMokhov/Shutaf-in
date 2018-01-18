package com.shutafin.controller;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.service.InitializationService;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class InitializationControllerTest extends BaseTestImpl{

    private static final String GET_LANGUAGE_URL = "/initialization/languages";
    private static final String GET_INITIALIZATION_RESPONSE_URL = "/initialization/all";
    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";

    private List<String> errorList;
    private Long validUser;

    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private InitializationService initializationService;

    @Before
    public void setUp(){
        validUser = 1L;
        errorList = new ArrayList<>();
        List<LanguageWeb> listLanguageWeb = new ArrayList<>();
        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.when(initializationService.findAllLanguages()).thenReturn(listLanguageWeb);
        Mockito.when(initializationService.getInitializationResponse(Mockito.anyLong())).thenReturn(new InitializationResponse());
    }

    @Test
    public void getLanguages_Positive(){
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_LANGUAGE_URL)
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getInitializationResponse_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_INITIALIZATION_RESPONSE_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getInitializationResponse_IncorrectSessionId(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_INITIALIZATION_RESPONSE_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());

    }
}

