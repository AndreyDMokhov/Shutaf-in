package com.shutafin.controller;


import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.repository.account.EmailChangeConfirmationRepository;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.EmailChangeConfirmationService;
import com.shutafin.service.PasswordService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
public class EmailChangeConfirmationControllerTest extends BaseTestImpl {

    private static final String EMAIL_CHANGE_REQUEST_URL = "/users/account/change-email-request";

    private static final String INP_PASSWORD_NOT_NULL = "INP.password.NotNull";
    private static final String INP_NEW_EMAIL_NOT_NULL = "INP.newEmail.NotNull";
    private List<String> errorList;

    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private EmailChangeConfirmationService emailChangeConfirmationService;

    @Before
    public void setUp() {
        Mockito.when(sessionManagementService.findUserWithValidSession(anyString())).thenReturn(new User());
        Mockito.doNothing().when(emailChangeConfirmationService).emailChangeRequest(any(User.class), any(EmailChangeConfirmationWeb.class));
        errorList = new ArrayList<>();
    }

    @Test
    public void emailChangeRequest_Positive() {
        List<HttpHeaders> headers = addSessionIdToHeader("40042cd8-51d0-4282-b431-36ee7f6dcaef");
        EmailChangeConfirmationWeb emailChangeConfirmationWeb = new EmailChangeConfirmationWeb();
        emailChangeConfirmationWeb.setUserPassword("22222222");
        emailChangeConfirmationWeb.setNewEmail("bbb@site.com");
        APIWebResponse response = getResponse(EMAIL_CHANGE_REQUEST_URL, emailChangeConfirmationWeb, HttpMethod.POST, headers);
        Assert.assertNull(response.getError());
    }

    @Test
    public void emailChangeRequestJson_AllFieldsNull() throws Exception {
        String emailChangeRequestJson = "{\"userPassword\":null,\"newEmail\":null}";
        errorList.add(INP_NEW_EMAIL_NOT_NULL);
        errorList.add(INP_PASSWORD_NOT_NULL);
        testEmailChangeConfirmationWeb(emailChangeRequestJson, errorList);
    }

    public List<HttpHeaders> addSessionIdToHeader(String sessionId) {
        List<HttpHeaders> headers = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("session_id", sessionId);
        headers.add(httpHeaders);
        return headers;
    }

    private void testEmailChangeConfirmationWeb(String json, List<String> errorList) {
        APIWebResponse response = getResponse(EMAIL_CHANGE_REQUEST_URL, json, HttpMethod.POST);
        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }
}