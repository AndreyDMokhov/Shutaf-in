package com.shutafin.controller;

import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.service.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
public class LoginControllerTest extends HelperTest{

    private static final String LOGIN_REQUEST_URL = "/login/";

    private static final String INP_EMAIL_NOT_BLANK = "INP.email.NotBlank";
    private static final String INP_EMAIL_LENGTH = "INP.email.Length";
    private static final String INP_EMAIL_EMAIL = "INP.email.Email";

    private static final String INP_PASSWORD_NOT_BLANK = "INP.password.NotBlank";
    private static final String INP_PASSWORD_LENGTH = "INP.password.Length";

    private List<String> errorList;

    @MockBean
    private LoginService loginService;

    @Before
    public void SetUp(){
        Mockito.when(loginService.getSessionIdByEmail(any(LoginWebModel.class))).thenReturn("b0f45f61-5a14-48c6-a86f-f793a5023441");
        errorList = new ArrayList<>();
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
    public void LoginRequestJson_IllegalEmail(){
        String loginWebModelJson = "{\"email\":\"gmail.com\",\"password\":\"111111Zz\"}";
        errorList.add(INP_EMAIL_EMAIL);
        testControllerInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Null(){
        String loginWebModelJson = "{\"email\":null,\"password\":null}";
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        testControllerInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Empty(){
        String loginWebModelJson = "{\"email\":\"\",\"password\":\"\"}";
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_PASSWORD_LENGTH);
        testControllerInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Whitespace(){
        String loginWebModelJson = "{\"email\":\" \",\"password\":\" \"}";
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_EMAIL_EMAIL);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_PASSWORD_LENGTH);
        testControllerInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_ExceededMaxLength(){
        String loginWebModelJson = "{\"email\":\"aaaaaaaaaalllllllllleeeeeeeeeexxxxxxxxxx@gmailx.com\"," +
                "\"password\":\"11111222223333344444555556\"}";
        errorList.add(INP_EMAIL_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        testControllerInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_ExceededMinLength(){
        String loginWebModelJson = "{\"email\":\"psw@gmail.com\",\"password\":\"1234567\"}";
        errorList.add(INP_PASSWORD_LENGTH);
        testControllerInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

}
