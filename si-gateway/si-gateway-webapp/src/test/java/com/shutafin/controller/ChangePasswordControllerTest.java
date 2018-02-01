package com.shutafin.controller;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.error.errors.InputValidationError;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountChangePasswordWeb;
import com.shutafin.service.ChangePasswordService;
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

@RunWith(SpringJUnit4ClassRunner.class)
public class ChangePasswordControllerTest extends BaseTestImpl {

    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final String CHANGE_PASSWORD_URL = "/users/password/change";
    private static final String CHANGE_PASSWORD_VALID_JSON_BODY = "{\"oldPassword\": \"oldPassword\", \"newPassword\": \"newPassword\"}";
    private static final String INP_NEW_PASSWORD_LENGTH = "INP.newPassword.Length";
    private static final String INP_OLD_PASSWORD_LENGTH = "INP.oldPassword.Length";
    private static final String INP_NEW_PASSWORD_NOT_BLANK = "INP.newPassword.NotBlank";
    private static final String INP_OLD_PASSWORD_NOT_BLANK = "INP.oldPassword.NotBlank";

    private List<String> errorList;
    private Long validUser;

    @MockBean
    private ChangePasswordService changePasswordService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp(){
        validUser = 1L;
        errorList = new ArrayList<>();
        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.doNothing().when(changePasswordService).changePassword(Mockito.any(AccountChangePasswordWeb.class), Mockito.anyLong());
    }

    @Test
    public void changePassword_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CHANGE_PASSWORD_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setJsonContext(CHANGE_PASSWORD_VALID_JSON_BODY)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void changePassword_IncorrectSessionId(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CHANGE_PASSWORD_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setJsonContext(CHANGE_PASSWORD_VALID_JSON_BODY)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void changePassword_AllFieldsEmpty_MinLength() {
        String accountChangePasswordWebJson = "{\"oldPassword\":\"1\", \"newPassword\":\"1\"}";
        errorList.add(INP_OLD_PASSWORD_NOT_BLANK);
        errorList.add(INP_NEW_PASSWORD_NOT_BLANK);
        errorList.add(INP_NEW_PASSWORD_LENGTH);
        errorList.add(INP_OLD_PASSWORD_LENGTH);
        changePasswordEqualsErrors(accountChangePasswordWebJson, errorList);
    }

    @Test
    public void changePassword_MaxLength(){
        String accountChangePasswordWebJson = "{\"oldPassword\":\"11111111112222222222333333\", \"newPassword\":\"11111111112222222222333333\"}";
        errorList.add(INP_NEW_PASSWORD_LENGTH);
        errorList.add(INP_OLD_PASSWORD_LENGTH);
        changePasswordEqualsErrors(accountChangePasswordWebJson, errorList);
    }


    private void changePasswordEqualsErrors(String json, List<String> errorList) {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CHANGE_PASSWORD_URL)
                .setHttpMethod(HttpMethod.PUT)
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
