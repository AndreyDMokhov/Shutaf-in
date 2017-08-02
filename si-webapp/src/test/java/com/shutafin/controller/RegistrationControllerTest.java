package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.RegistrationService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.system.BaseTestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RegistrationControllerTest extends BaseTestImpl {

    private static final String REGISTRATION_REQUEST_URL = "/users/registration/request";
    private static final String CONFIRM_REGISTRATION_REQUEST_URL = "/users/registration/confirmation/";

    private static final String INP_FIRST_NAME_NOT_NULL = "INP.firstName.NotNull";
    private static final String INP_LAST_NAME_NOT_NULL = "INP.lastName.NotNull";
    private static final String INP_PASSWORD_NOT_NULL = "INP.password.NotNull";
    private static final String INP_USER_LANGUAGE_ID_NOT_NULL = "INP.userLanguageId.NotNull";

    private static final String INP_FIRST_NAME_LENGTH = "INP.firstName.Length";
    private static final String INP_LAST_NAME_LENGTH = "INP.lastName.Length";
    private static final String INP_EMAIL_LENGTH = "INP.email.Length";
    private static final String INP_PASSWORD_LENGTH = "INP.password.Length";

    private static final String INP_EMAIL_NOT_EMPTY = "INP.email.NotEmpty";
    private static final String INP_EMAIL_EMAIL = "INP.email.Email";

    private static final String INP_USER_LANGUAGE_ID_MIN = "INP.userLanguageId.Min";

    private List<String> errorList;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {
        Mockito.doNothing().when(registrationService).save(any(RegistrationRequestWeb.class));
        Mockito.when(registrationService.confirmRegistration(anyString())).thenReturn(new User());
        Mockito.when(sessionManagementService.generateNewSession(any(User.class))).thenReturn("648c208e-bfc8-49a7-9206-dfca5a8d9759");
        errorList = new ArrayList<>();
    }

    @Test
    public void confirmRegistration_Positive(){
        APIWebResponse response = getResponse(CONFIRM_REGISTRATION_REQUEST_URL+"1a424de3-3671-420f-a8e2-ee97158f9ea2",
                HttpMethod.GET);
        Assert.assertNull(response.getError());
    }

    @Test
    public void registrationRequestObject_Positive() {
        RegistrationRequestWeb registrationRequestWeb = new RegistrationRequestWeb();
        registrationRequestWeb.setEmail("email@site.com");
        registrationRequestWeb.setFirstName("Peter");
        registrationRequestWeb.setLastName("Dale");
        registrationRequestWeb.setPassword("12345678");
        registrationRequestWeb.setUserLanguageId(1);

        APIWebResponse response = getResponse(
                REGISTRATION_REQUEST_URL,
                registrationRequestWeb,
                HttpMethod.POST);

        Assert.assertNull(response.getError());
    }

    @Test
    public void registrationRequestJson_AllFieldsNull() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"userLanguageId\":null}";
        errorList.add(INP_FIRST_NAME_NOT_NULL);
        errorList.add(INP_LAST_NAME_NOT_NULL);
        errorList.add(INP_EMAIL_NOT_EMPTY);
        errorList.add(INP_PASSWORD_NOT_NULL);
        errorList.add(INP_USER_LANGUAGE_ID_NOT_NULL);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_AllEmptyFields() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"\",\"lastName\":\"\",\"email\":\"\",\"password\":\"\",\"userLanguageId\":\"\"}";
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_EMAIL_NOT_EMPTY);
        errorList.add(INP_PASSWORD_LENGTH);
        errorList.add(INP_USER_LANGUAGE_ID_NOT_NULL);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_AllWhitespaceFields() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\" \",\"lastName\":\" \",\"email\":\" \",\"password\":\" \",\"userLanguageId\":\" \"}";
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_EMAIL_EMAIL);
        errorList.add(INP_PASSWORD_LENGTH);
        errorList.add(INP_USER_LANGUAGE_ID_NOT_NULL);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_ExceededMaxLength() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"ppppppppppeeeeeeeeeettttttttttrrrrrrrrrroooooooooov\"," +
                "\"lastName\":\"ppppppppppeeeeeeeeeettttttttttrrrrrrrrrroooooooooov\"," +
                "\"email\":\"ppppppppppeeeeeeeeeettttttttttrrrrrrrrrr@gmailx.com\"," +
                "\"password\":\"11111222223333344444555556\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_EMAIL_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_ExceededMinLength() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"pp\",\"lastName\":\"pp\",\"email\":\"pp@gmail.com\",\"password\":\"1234567\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_IllegalEmail() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"gmail\",\"password\":\"12345678\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_EMAIL_EMAIL);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_IllegalUserLanguageId() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"petr@gmail\",\"password\":\"12345678\",\"userLanguageId\":\"0\"}";
        errorList.add(INP_USER_LANGUAGE_ID_MIN);
        testRegistrationRequestWeb(registrationRequestWebJson, errorList);
    }

    private void testRegistrationRequestWeb(String json, List<String> errorList){
        APIWebResponse response = getResponse(REGISTRATION_REQUEST_URL, json, HttpMethod.POST);
        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }
}
