package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.service.LoginService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
public class LoginControllerTest extends BaseTestImpl {

    private static final String LOGIN_REQUEST_URL = "/login/";
    private static final String SESSION_ID = "bce54f17-624d-4b59-a627-ff5cf42c9078";

    private static final String INP_EMAIL_NOT_BLANK = "INP.email.NotBlank";
    private static final String INP_EMAIL_LENGTH = "INP.email.Length";
    private static final String INP_EMAIL_EMAIL = "INP.email.Email";
    private static final String INP_PASSWORD_NOT_BLANK = "INP.password.NotBlank";
    private static final String INP_PASSWORD_LENGTH = "INP.password.Length";

    private List<String> errorList;

    @MockBean
    private LoginService loginService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void SetUp() {
        Mockito.when(loginService.getUserByLoginWebModel(any(LoginWebModel.class))).thenReturn(createUser());
        Mockito.when(sessionManagementService.generateNewSession(any(User.class))).thenReturn(SESSION_ID);
        errorList = new ArrayList<>();
    }

    @Test
    public void LoginRequestObject_Positive() {
        LoginWebModel loginWebModel = new LoginWebModel();
        loginWebModel.setEmail("email@site.com");
        loginWebModel.setPassword("12345678");
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(LOGIN_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setRequestObject(loginWebModel)
                .build();
        MockHttpServletResponse mockHttpServletResponse = getServletResponse(request);
        APIWebResponse response = getResponse(mockHttpServletResponse);
        Assert.assertNull(response.getError());
        Assert.assertEquals(mockHttpServletResponse.getHeader("session_id"), SESSION_ID);
    }

    @Test
    public void LoginRequestJson_IllegalEmail() {
        String loginWebModelJson = "{\"email\":\"gmail.com\",\"password\":\"111111Zz\"}";
        errorList.add(INP_EMAIL_EMAIL);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_IllegalPassword() {
        String loginWebModelJson = "{\"email\":\"email@site.com\",\"password\":\"1111111\"}";
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_IllegalEmailAndPassword() {
        String loginWebModelJson = "{\"email\":\"site.com\",\"password\":\"1111111\"}";
        errorList.add(INP_EMAIL_EMAIL);
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Null() {
        String loginWebModelJson = "{\"email\":null,\"password\":null}";
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Empty() {
        String loginWebModelJson = "{\"email\":\"\",\"password\":\"\"}";
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_Whitespace() {
        String loginWebModelJson = "{\"email\":\" \",\"password\":\" \"}";
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_EMAIL_EMAIL);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_ExceededMaxLength() {
        String loginWebModelJson = "{\"email\":\"aaaaaaaaaalllllllllleeeeeeeeeexxxxxxxxxx@gmailx.com\"," +
                "\"password\":\"11111222223333344444555556\"}";
        errorList.add(INP_EMAIL_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    @Test
    public void LoginRequestJson_ExceededMinLength() {
        String loginWebModelJson = "{\"email\":\"psw@gmail.com\",\"password\":\"1234567\"}";
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(LOGIN_REQUEST_URL, loginWebModelJson, errorList);
    }

    private User createUser() {
        User user = new User();
        user.setId(2L);
        user.setEmail("a@a");
        user.setFirstName("User");
        user.setLastName("User");
        user.setCreatedDate(Date.from(Instant.now()));
        return user;
    }

}
