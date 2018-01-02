package com.shutafin.controller;

import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.user.PasswordWeb;
import com.shutafin.service.ResetPasswordService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Matchers.anyString;


@RunWith(SpringJUnit4ClassRunner.class)
public class ResetPasswordControllerTest extends BaseTestImpl {

    private static final String RESET_PASSWORD_REQUEST_URL = "/reset-password/request";
    private static final String RESET_PASSWORD_VALIDATION_URL = "/reset-password/validate/";
    private static final String PASSWORD_CHANGE_URL = "/reset-password/change/" ;
    private static final String LINK = "link";
    private static final String RESET_PASSWORD_JSON_BODY = "{\"email\": \"aaa@aaaa\"}";
    private static final String PASSWORD_CHANGE_JSON_BODY = "{\"newPassword\": \"aaa@aaaa\"}";
    private static final String RESET_PASSWORD_JSON_BODY_NOT_EMAIL = "{\"email\": \"aaa\"}";
    private static final String PASSWORD_CHANGE_JSON_BODY_NOT_EMAIL = "{\"newPassword\": \"aaa\"}" ;

    @MockBean
    private ResetPasswordService resetPasswordService;

    @Before
    public void setUp(){
        Mockito.doNothing().when(resetPasswordService).resetPasswordRequest(Mockito.any(AccountEmailRequest.class));
        Mockito.doNothing().when(resetPasswordService).resetPasswordValidation(anyString());
        Mockito.doNothing().when(resetPasswordService).passwordChange(Mockito.any(PasswordWeb.class), anyString());
    }

    @Test
    public void resetPasswordRequest_Positive(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(RESET_PASSWORD_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(RESET_PASSWORD_JSON_BODY)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void resetPasswordRequest_notEmail(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(RESET_PASSWORD_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(RESET_PASSWORD_JSON_BODY_NOT_EMAIL)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.INPUT.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void resetPasswordValidation_Positive(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(RESET_PASSWORD_VALIDATION_URL + LINK)
                .setHttpMethod(HttpMethod.GET)
                .build();

        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void resetPasswordValidation_UrlWithAWhitespace(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(RESET_PASSWORD_VALIDATION_URL + " ")
                .setHttpMethod(HttpMethod.GET)
                .build();

        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.RESOURCE_NOT_FOUND_ERROR.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void passwordChange_Positive(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(PASSWORD_CHANGE_URL + LINK)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(PASSWORD_CHANGE_JSON_BODY)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void passwordChange_notEmail(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(PASSWORD_CHANGE_URL + LINK)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(PASSWORD_CHANGE_JSON_BODY_NOT_EMAIL)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.INPUT.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }

    @Test
    public void passwordChange_UrlWithAWhitespace(){
        ControllerRequest request =ControllerRequest.builder()
                .setUrl(PASSWORD_CHANGE_URL + " ")
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(PASSWORD_CHANGE_JSON_BODY)
                .build();

        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(ErrorType.RESOURCE_NOT_FOUND_ERROR.getErrorCodeType(), apiResponse.getError().getErrorTypeCode());
    }



}
