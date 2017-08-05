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
import com.shutafin.system.ControllerRequest;
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
    private static final String SESSION_ID_HEADER_NAME = "session_id";


    private Language language;
    private User user;
    private ArrayList<String> expectedError;

    @MockBean
    private UserLanguageService userLanguageService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {
        language = new Language();
        user = new User();
        Mockito.doNothing().when(userLanguageService).updateUserLanguage(Mockito.any(UserLanguageWeb.class), Mockito.any(User.class));
        Mockito.when(userLanguageService.findUserLanguage(user)).thenReturn(language);
        expectedError = new ArrayList<>();
    }

    @Test
    public void updateRequestJson_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(VALID_JSON)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNull(response.getError());
    }

    @Test
    public void updateRequestJson_LangId_0(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(LANG_ID_0)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        expectedError.add("INP.id.Min");
        Assert.assertEquals(inputValidationError.getErrors(), expectedError);
    }

    @Test
    public void updateRequestJson_LangIdIsNull(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(LANG_ID_NULL)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        expectedError.add("INP.id.NotNull");
        Assert.assertEquals(inputValidationError.getErrors(), expectedError);
    }

    @Test
    public void updateRequestJson_WrongSession(){
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(VALID_JSON)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }

    @Test
    public void getRequestJson_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(headers)
                .setResponseClass(Language.class)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNull(response.getError());
        Assert.assertEquals(language, response.getData());
    }

    @Test
    public void getRequestJson_Wrong_Session(){
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(headers)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }

    public List<HttpHeaders> addSessionIdToHeader(String sessionId){
        List<HttpHeaders> headers = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SESSION_ID_HEADER_NAME, sessionId);
        headers.add(httpHeaders);
        return headers;
    }
}
