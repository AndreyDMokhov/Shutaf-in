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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LoginControllerTest extends BaseTestImpl{

    private static final String LOGIN_REQUEST_URL = "/login/";

    private static final String INP_EMAIL_NOT_EMPTY = "INP.email.NotEmpty";
    private static final String INP_EMAIL_LENGTH = "INP.email.Length";
    private static final String INP_EMAIL_EMAIL = "INP.email.Email";
    private static final String INP_PASSWORD_LENGTH = "INP.password.Length";
    private static final String INP_PASSWORD_NOT_NULL = "INP.password.NotNull";

    @Autowired
    private LoginService loginService;

    @Before
    public void SetUp(){
        Mockito.when(loginService.getSessionIdByEmail(any(LoginWebModel.class))).thenReturn("b0f45f61-5a14-48c6-a86f-f793a5023441");
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
    public void LoginRequestJson_IncorrectEmail(){
        String loginWebModelJson = "{\"email\":\"gmail.com\",\"password\":\"111111Zz\"}";
        List<String> errorList = new ArrayList<>();
        errorList.add(INP_EMAIL_EMAIL);
        testLoginWebModel(loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Null(){
        String loginWebModelJson = "{\"email\":null,\"password\":null}";
        List<String> errorList = new ArrayList<>();
        errorList.add(INP_EMAIL_NOT_EMPTY);
        errorList.add(INP_PASSWORD_NOT_NULL);
        testLoginWebModel(loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Empty(){
        String loginWebModelJson = "{\"email\":\"\",\"password\":\"\"}";
        List<String> errorList = new ArrayList<>();
        errorList.add(INP_EMAIL_NOT_EMPTY);
        errorList.add(INP_PASSWORD_LENGTH);
        testLoginWebModel(loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Blank(){
        String loginWebModelJson = "{\"email\":\" \",\"password\":\" \"}";
        List<String> errorList = new ArrayList<>();
        errorList.add(INP_EMAIL_EMAIL);
        errorList.add(INP_PASSWORD_LENGTH);
        testLoginWebModel(loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_EmailLengthMore50_PasswordLengthMore25(){
        String loginWebModelJson = "{\"email\":\"aaaaaaaaaalllllllllleeeeeeeeeexxxxxxxxxx@gmailx.com\"," +
                "\"password\":\"11111222223333344444555556\"}";
        List<String> errorList = new ArrayList<>();
        errorList.add(INP_EMAIL_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        testLoginWebModel(loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_PasswordLengthLess8(){
        String loginWebModelJson = "{\"email\":\"psw@gmail.com\",\"password\":\"1234567\"}";
        List<String> errorList = new ArrayList<>();
        errorList.add(INP_PASSWORD_LENGTH);
        testLoginWebModel(loginWebModelJson, errorList);
    }

    private void testLoginWebModel(String json, List<String> errorList){
        APIWebResponse response = getResponse(LOGIN_REQUEST_URL, json, HttpMethod.POST);
        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }
}
