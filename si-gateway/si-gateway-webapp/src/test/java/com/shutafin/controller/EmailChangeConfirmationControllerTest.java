package com.shutafin.controller;


import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.model.web.user.GatewayEmailChangedResponse;
import com.shutafin.service.EmailChangeConfirmationService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyString;


@RunWith(SpringJUnit4ClassRunner.class)
public class EmailChangeConfirmationControllerTest extends BaseTestImpl {

    private static final String SESSION_ID_HEADER_NAME = "session_id";
    private static final String EMAIL_CHANGE_REQUEST_URL = "/users/account/change-email-request/";
    private static final String EMAIL_CHANGE_CONFIRMATION_URL = "/users/account/change-email-confirmation/";
    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final String EMAIL_CHANGE_VALID_JSON_BODY = "{\"userPassword\": \"11111111\", \"emailChange\": \"aaa@aaaa\"}";

    private static final String INP_PASSWORD_NOT_BLANK = "INP.userPassword.NotBlank";
    private static final String INP_NEW_EMAIL_NOT_BLANK = "INP.emailChange.NotBlank";

    private static final String INP_PASSWORD_LENGTH = "INP.userPassword.Length";
    private static final String INP_NEW_EMAIL_LENGTH = "INP.emailChange.Length";

    private static final String INP_NEW_EMAIL_EMAIL = "INP.emailChange.Email";

    private List<String> errorList;
    private Long validUser;

    @MockBean
    private SessionManagementService sessionManagementService;
    @MockBean
    private EmailChangeConfirmationService emailChangeConfirmationService;

    @Before
    public void setUp() {
        validUser = 1L;
        errorList = new ArrayList<>();

        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
//        Mockito.doNothing().when(emailChangeConfirmationService)
//                .emailChangeRequest(Mockito.any(User.class), Mockito.any(EmailChangeConfirmationWeb.class));
        Mockito.when(emailChangeConfirmationService.
                emailChangeConfirmation(anyString())).thenReturn(new GatewayEmailChangedResponse());
    }

    @Test
    public void emailChangeConfirmation_Positive() {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(EMAIL_CHANGE_CONFIRMATION_URL + "1a424de3-3671-420f-a8e2-ee97158f9ea2")
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void emailChangeConfirmation_UrlWithAWhitespace() {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(EMAIL_CHANGE_CONFIRMATION_URL + " ")
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.RESOURCE_NOT_FOUND_ERROR.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void emailChangeRequest_Positive() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);

        ControllerRequest request = ControllerRequest.builder()
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setJsonContext(EMAIL_CHANGE_VALID_JSON_BODY)
                .setHttpMethod(HttpMethod.POST)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void emailChangeRequest_IncorrectSessionId() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setJsonContext(EMAIL_CHANGE_VALID_JSON_BODY)
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void emailChangeRequest_IncorrectHeaderName() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set("sesion", VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setJsonContext(EMAIL_CHANGE_VALID_JSON_BODY)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void emailChangeRequest_AllFieldsNull() {
        String emailChangeRequestRequestWebJson = "{\"userPassword\":null,\"emailChange\":null}";
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_NEW_EMAIL_NOT_BLANK);
        testEmailChangeConfirmationWeb(emailChangeRequestRequestWebJson, errorList);
    }

    @Test
    public void emailChangeRequest_AllEmptyFields() {
        String emailChangeRequestRequestWebJson = "{\"userPassword\":\"\",\"emailChange\":\"\"}";
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_NEW_EMAIL_NOT_BLANK);
        testEmailChangeConfirmationWeb(emailChangeRequestRequestWebJson, errorList);
    }

    @Test
    public void emailChangeRequest_AllWhitespaceFields() {
        String emailChangeRequestRequestWebJson = "{\"userPassword\":\" \",\"emailChange\":\" \"}";
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_NEW_EMAIL_NOT_BLANK);
        errorList.add(INP_NEW_EMAIL_EMAIL);
        testEmailChangeConfirmationWeb(emailChangeRequestRequestWebJson, errorList);
    }

    @Test
    public void emailChangeRequest_ExceededMaxLength() {
        String emailChangeRequestRequestWebJson = "{\"userPassword\":\"iiiiiiiiiivvvvvvvvvvaaaaaaaaaannnnnnnnnnoooooooooov\"," +
                "\"emailChange\":\"iiiiiiiiiivvvvvvvvvvaaaaaaaaaannnnnnnnnnoooooooooov\"}";
        errorList.add(INP_PASSWORD_LENGTH);
        errorList.add(INP_NEW_EMAIL_LENGTH);
        errorList.add(INP_NEW_EMAIL_EMAIL);
        testEmailChangeConfirmationWeb(emailChangeRequestRequestWebJson, errorList);
    }

    @Test
    public void emailChangeRequest_IllegalEmail() {
        String emailChangeRequestRequestWebJson = "{\"userPassword\":\"alex\",\"emailChange\":\"gmail\"}";
        errorList.add(INP_NEW_EMAIL_EMAIL);
        testEmailChangeConfirmationWeb(emailChangeRequestRequestWebJson, errorList);
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("Alexander");
        user.setLastName("Matsievsky");
        user.setEmail("matsievsky@gmail.com");
        return user;
    }

    private void testEmailChangeConfirmationWeb(String json, List<String> errorList) {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setJsonContext(json)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(apiResponse.getError().getErrorTypeCode(),
                ErrorType.INPUT.getErrorCodeType());

        InputValidationError inputValidationError = (InputValidationError) apiResponse.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }
}