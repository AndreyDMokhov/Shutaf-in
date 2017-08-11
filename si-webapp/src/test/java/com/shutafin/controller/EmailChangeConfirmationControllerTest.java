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


import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
public class EmailChangeConfirmationControllerTest extends BaseTestImpl {

    private static final String EMAIL_CHANGE_REQUEST_URL = "/users/account/change-email-request/";
    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String SESSION_ID_HEADER_NAME = "session_id";
    private static final String ADD_IMAGE_VALID_JSON_BODY = "{\"userPassword\": \"11111111\", \"newEmail\": \"aaa@aaaa\"}";
    private static final String INVALID_SESSION_ID = "invalidsessionid";

    private static final String INP_PASSWORD_NOT_NULL = "INP.password.NotNull";
    private static final String INP_NEW_EMAIL_NOT_NULL = "INP.newEmail.NotNull";
    private List<String> errorList;

    private User validUser;

    @MockBean
    private SessionManagementService sessionManagementService;
    @MockBean
    private EmailChangeConfirmationService emailChangeConfirmationService;

    @Before
    public void setUp() {
        validUser = createUser();
        Mockito.when(sessionManagementService.findUserWithValidSession(Mockito.anyString())).thenReturn(validUser);
        Mockito.doNothing().when(emailChangeConfirmationService)
                .emailChangeRequest(Mockito.any(User.class), Mockito.any(EmailChangeConfirmationWeb.class));
    }
    @Test
    public void emailChangeRequest_Positive() {
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setJsonContext(ADD_IMAGE_VALID_JSON_BODY)
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void emailChangeRequest_IncorrectSessionId(){
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set(SESSION_ID_HEADER_NAME, INVALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setJsonContext(ADD_IMAGE_VALID_JSON_BODY)
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void emailChangeRequest_IncorrectHeader(){
        List<HttpHeaders> sessionHeaders = new ArrayList<>();
        sessionHeaders.add(new HttpHeaders());
        sessionHeaders.get(0).set("sesion", VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setJsonContext(ADD_IMAGE_VALID_JSON_BODY)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.AUTHENTICATION.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void emailChangeRequest_AllFieldsNull(){
        String emailChangeRequestRequestWebJson = "{\"userPassword\":null,\"newEmail\":null}";
        errorList.add(INP_NEW_EMAIL_NOT_NULL);
        errorList.add(INP_PASSWORD_NOT_NULL);
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
        ControllerRequest request = ControllerRequest.builder()
                .setHttpMethod(HttpMethod.POST)
                .setUrl(EMAIL_CHANGE_REQUEST_URL)
                .setJsonContext(json)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNotNull(apiResponse.getError());
        InputValidationError inputValidationError = (InputValidationError) apiResponse.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }
}