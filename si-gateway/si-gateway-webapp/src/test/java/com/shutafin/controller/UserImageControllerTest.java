package com.shutafin.controller;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserImageService;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class UserImageControllerTest extends BaseTestImpl {

    private static final String SESSION_ID_HEADER_NAME = "session_id";
    private static final String ADD_IMAGE_VALID_JSON_BODY = "{\"image\": \"some image in base64\"}";
    private static final String USER_IMAGE_REQUEST_URL = "/images/";
    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final Long VALID_USER_ID = 1L;
    private static final Long VALID_USER_IMAGE_ID = 1L;
    private static final Long INVALID_USER_IMAGE_ID = 99L;
    private static final String VALID_IMAGE_IN_BASE64 = "valid image in base64";


    @MockBean
    private UserImageService userImageService;
    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {

        Long validUser = 1L;

        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.when(userImageService.addUserImage(new AccountUserImageWeb(), VALID_USER_ID))
                .thenReturn(new AccountUserImageWeb());
        Mockito.when(userImageService.getCompressedUserImage(Mockito.anyLong()))
                .thenReturn(new AccountUserImageWeb());
        Mockito.when(userImageService.getCompressedUserImage(INVALID_USER_IMAGE_ID))
                .thenThrow(new ResourceNotFoundException());

        Mockito.when(userImageService.getOriginalUserImage(validUser))
                .thenThrow(new ResourceNotFoundException());

        Mockito.doNothing().when(userImageService).deleteUserImage(validUser, VALID_USER_IMAGE_ID);
        Mockito.doThrow(new ResourceNotFoundException())
                .when(userImageService).deleteUserImage(validUser, INVALID_USER_IMAGE_ID);

    }

    @Test
    public void addUserImage_Positive() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(ADD_IMAGE_VALID_JSON_BODY)
                .setResponseClass(AccountUserImageWeb.class)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void addUserImage_IncorrectSessionId() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(ADD_IMAGE_VALID_JSON_BODY)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void addUserImage_IncorrectHeader() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set("sesion", VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(ADD_IMAGE_VALID_JSON_BODY)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void addUserImage_IncorrectInput() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, VALID_SESSION_ID);
        String jsonBody = "{}";
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(jsonBody)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.INPUT.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void getUserImage_Positive() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL + VALID_USER_IMAGE_ID)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .setResponseClass(AccountUserImageWeb.class)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getUserImage_IncorrectUserImageId() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL + INVALID_USER_IMAGE_ID)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .setResponseClass(AccountUserImageWeb.class)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.RESOURCE_NOT_FOUND_ERROR.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void getUserImage_IncorrectSessionId() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL + VALID_USER_IMAGE_ID)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .setResponseClass(AccountUserImageWeb.class)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void deleteUserImage_Positive() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL + VALID_USER_IMAGE_ID)
                .setHttpMethod(HttpMethod.DELETE)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void deleteUserImage_IncorrectUserImageId() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL + INVALID_USER_IMAGE_ID)
                .setHttpMethod(HttpMethod.DELETE)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.RESOURCE_NOT_FOUND_ERROR.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void deleteUserImage_IncorrectSessionId() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_IMAGE_REQUEST_URL + VALID_USER_IMAGE_ID)
                .setHttpMethod(HttpMethod.DELETE)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }






}
