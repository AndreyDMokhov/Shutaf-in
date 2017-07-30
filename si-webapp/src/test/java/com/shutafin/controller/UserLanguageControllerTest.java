package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserLanguageService;
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


@RunWith(SpringRunner.class)
public class UserLanguageControllerTest extends BaseTestImpl  {

    private static final String REGISTRATION_REQUEST_URL = "/user/account/language";
    private static final String VALID_JSON = "{\"id\":\"1\"}";
    private static final String LANG_ID_0 = "{\"id\":\"0\"}";
    private static final String LANG_ID_NULL = "{\"id\":\"\"}";
    private static final String VALID_SESSION = "40042cd8-51d0-4282-b431-36ee7f6dcaef";
    private static final String INVALID_SESSION = "";


    private Language language;
    private ArrayList<String> expectedError;

    @MockBean
    private UserLanguageService userLanguageService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {
        Mockito.doNothing().when(userLanguageService).updateUserLanguage(Mockito.any(UserLanguageWeb.class), Mockito.any(User.class));
        Mockito.when(userLanguageService.findUserLanguage(Mockito.any(User.class))).thenReturn(language);
        expectedError = new ArrayList<>();
    }

    @Test
    public void updateRequestJson_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                VALID_JSON,
                HttpMethod.PUT,
                headers);

        Assert.assertNull(response.getError());
    }

    @Test
    public void updateRequestJson_LangId_0(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                LANG_ID_0,
                HttpMethod.PUT,
                headers);

        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        expectedError.add("INP.id.Min");
        Assert.assertEquals(inputValidationError.getErrors(), expectedError);
    }

    @Test
    public void updateRequestJson_LangIdIsNull(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                LANG_ID_NULL,
                HttpMethod.PUT,
                headers);

        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        expectedError.add("INP.id.NotNull");
        Assert.assertEquals(inputValidationError.getErrors(), expectedError);
    }

    @Test
    public void updateRequestJson_WrongSession(){
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                VALID_JSON,
                HttpMethod.PUT,
                headers);

        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }

    @Test
    public void getRequestJson_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                HttpMethod.GET,
                headers);

        Assert.assertNull(response.getError());
        Assert.assertEquals(language, response.getData());
    }

    @Test
    public void getRequestJson_Wrong_Session(){
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                HttpMethod.GET,
                headers);

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
