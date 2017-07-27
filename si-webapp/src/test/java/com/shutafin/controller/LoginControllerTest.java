package com.shutafin.controller;

import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.service.LoginService;
import com.shutafin.system.BaseTestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LoginControllerTest extends BaseTestImpl{

    private static final String LOGIN_REQUEST_URL = "/login/";
//    private static final String CORRECT_SESSION_ID = "b0f45f61-5a14-48c6-a86f-f793a5023441";
    private static final String INP_EMAIL_NOT_BLANK = "INP.email.NotBlank";
    private static final String INP_EMAIL_LENGTH = "INP.email.Length";
    private static final String INP_EMAIL_EMAIL = "INP.email.Email";
    private static final String INP_PASSWORD_NOT_BLANK = "INP.password.NotBlank";
    private static final String INP_PASSWORD_LENGTH = "INP.password.Length";

    @Autowired
    private LoginService loginService;

    @Before
    public void SetUp(){
        Mockito.when(loginService.getSessionIdByEmail(any(LoginWebModel.class))).thenReturn("");
    }

    @Test
    public void LoginRequestObject_Positive(){
        LoginWebModel loginWebModel = new LoginWebModel();
        loginWebModel.setEmail("email@site.com");
        loginWebModel.setPassword("12345678");

        APIWebResponse response = getResponse(LOGIN_REQUEST_URL, loginWebModel, HttpMethod.POST);

        Assert.assertNull(response.getError());
    }

    @Test
    public void LoginRequestJson_EmailNull(){
        String loginWebModelJson = "{\"email\":\"\",\"password\":\"111111Zz\"}";
        testLoginWebModel(loginWebModelJson, INP_EMAIL_NOT_BLANK);
    }

    @Test
    public void LoginRequestJson_EmailLengthMore50(){
        String loginWebModelJson = "{\"email\":\"aaaaaaaaaalllllllllleeeeeeeeeexxxxxxxxxx@gmailx.com\",\"password\":\"111111Zz\"}";
        testLoginWebModel(loginWebModelJson, INP_EMAIL_LENGTH);
    }

    @Test
    public void LoginRequestJson_IncorrectEmail(){
        String loginWebModelJson = "{\"email\":\"gmail.com\",\"password\":\"111111Zz\"}";
        testLoginWebModel(loginWebModelJson, INP_EMAIL_EMAIL);
    }

    @Test
    public void LoginRequestJson_PasswordNull(){
        String loginWebModelJson = "{\"email\":\"psw@gmail.com\",\"password\":\"\"}";
        testLoginWebModel(loginWebModelJson, INP_PASSWORD_NOT_BLANK);
    }

    @Test
    public void LoginRequestJson_PasswordLengthMore25(){
        String loginWebModelJson = "{\"email\":\"psw@gmail.com\",\"password\":\"11111222223333344444555556\"}";
        testLoginWebModel(loginWebModelJson, INP_PASSWORD_LENGTH);
    }

    @Test
    public void LoginRequestJson_PasswordLengthLess8(){
        String loginWebModelJson = "{\"email\":\"psw@gmail.com\",\"password\":\"1234567\"}";
        testLoginWebModel(loginWebModelJson, INP_PASSWORD_LENGTH);
    }

    private void testLoginWebModel(String json, String error_description){
        APIWebResponse response = getResponse(LOGIN_REQUEST_URL, json, HttpMethod.POST);
        Assert.assertNotNull(response);
        InputValidationError error = (InputValidationError) response.getError();
        Assert.assertEquals(error.getErrors().get(0), error_description);
    }
}
