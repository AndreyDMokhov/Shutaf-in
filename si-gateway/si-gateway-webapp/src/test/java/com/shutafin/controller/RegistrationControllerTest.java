package com.shutafin.controller;

import com.shutafin.model.error.ErrorResponse;
import com.shutafin.model.error.ErrorType;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.RegistrationService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

@RunWith(SpringRunner.class)
public class RegistrationControllerTest extends BaseTestImpl {

    private static final String REGISTRATION_REQUEST_URL = "/users/registration/request";
    private static final String CONFIRM_REGISTRATION_REQUEST_URL = "/users/registration/confirmation/";
    private static final String SESSION_ID = "bce54f17-624d-4b59-a627-ff5cf42c9078";

    private static final String INP_FIRST_NAME_NOT_BLANK = "INP.firstName.NotBlank";
    private static final String INP_LAST_NAME_NOT_BLANK = "INP.lastName.NotBlank";
    private static final String INP_EMAIL_NOT_BLANK = "INP.email.NotBlank";
    private static final String INP_PASSWORD_NOT_BLANK = "INP.password.NotBlank";
    private static final String INP_USER_LANGUAGE_ID_NOT_NULL = "INP.userLanguageId.NotNull";

    private static final String INP_FIRST_NAME_LENGTH = "INP.firstName.Length";
    private static final String INP_LAST_NAME_LENGTH = "INP.lastName.Length";
    private static final String INP_EMAIL_LENGTH = "INP.email.Length";
    private static final String INP_PASSWORD_LENGTH = "INP.password.Length";

    private static final String INP_EMAIL_EMAIL = "INP.email.Email";

    private static final String INP_USER_LANGUAGE_ID_MIN = "INP.userLanguageId.Min";
    private static final String SYS_TYPE_ERROR = "SYS";

    private List<String> errorList;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {
        Mockito.doNothing().when(registrationService).registerUser(any(AccountRegistrationRequest.class));
        Mockito.doNothing().when(registrationService).confirmRegistrationUser(anyString());
        Mockito.when(sessionManagementService.generateNewSession(any(AccountUserWeb.class))).thenReturn(SESSION_ID);
        errorList = new ArrayList<>();
    }

    @Test
    public void confirmRegistration_Positive() {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CONFIRM_REGISTRATION_REQUEST_URL + "da1b10db-5727-4474-8700-db6cb6f26cf7")
                .setHttpMethod(HttpMethod.GET)
                .build();
        MockHttpServletResponse mockHttpServletResponse = getServletResponse(request);
        APIWebResponse response = getResponse(mockHttpServletResponse);
        Assert.assertNull(response.getError());
    }

    @Test
    public void confirmRegistration_UrlWithAWhitespace() {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CONFIRM_REGISTRATION_REQUEST_URL + " ")
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNotNull(response.getError());
        Assert.assertEquals(ErrorType.RESOURCE_NOT_FOUND_ERROR.getErrorCodeType(), response.getError().getErrorTypeCode());
    }

    @Test
    public void registrationRequestObject_Positive() {
        RegistrationRequestWeb registrationRequestWeb = new RegistrationRequestWeb();
        registrationRequestWeb.setEmail("email@site.com");
        registrationRequestWeb.setFirstName("Peter");
        registrationRequestWeb.setLastName("Dale");
        registrationRequestWeb.setPassword("12345678");
        registrationRequestWeb.setUserLanguageId(1);

        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setRequestObject(registrationRequestWeb)
                .build();

        APIWebResponse response = getResponse(request);
        Assert.assertNull(response.getError());
    }

    @Test
    public void registrationRequestJson_AllFieldsNull() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"userLanguageId\":null}";
        errorList.add(INP_FIRST_NAME_NOT_BLANK);
        errorList.add(INP_LAST_NAME_NOT_BLANK);
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_USER_LANGUAGE_ID_NOT_NULL);
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_AllEmptyFields() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"\",\"lastName\":\"\",\"email\":\"\",\"password\":\"\",\"userLanguageId\":\"\"}";
        errorList.add(INP_FIRST_NAME_NOT_BLANK);
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_NOT_BLANK);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_PASSWORD_LENGTH);
        errorList.add(INP_USER_LANGUAGE_ID_NOT_NULL);
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_AllWhitespaceFields() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\" \",\"lastName\":\" \",\"email\":\" \",\"password\":\" \",\"userLanguageId\":\" \"}";
        errorList.add(INP_FIRST_NAME_NOT_BLANK);
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_NOT_BLANK);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_EMAIL_NOT_BLANK);
        errorList.add(INP_EMAIL_EMAIL);
        errorList.add(INP_PASSWORD_NOT_BLANK);
        errorList.add(INP_PASSWORD_LENGTH);
        errorList.add(INP_USER_LANGUAGE_ID_NOT_NULL);
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
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
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_ExceededMinLength() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"pp\",\"lastName\":\"pp\",\"email\":\"pp@gmail.com\",\"password\":\"1234567\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_IllegalEmail() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"gmail\",\"password\":\"12345678\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_EMAIL_EMAIL);
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_IllegalUserLanguageId() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"petr@gmail\",\"password\":\"12345678\",\"userLanguageId\":\"0\"}";
        errorList.add(INP_USER_LANGUAGE_ID_MIN);
        assertInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestObject_LanguageIdWithAStringValue() {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"petr@gmail\",\"password\":\"12345678\",\"userLanguageId\":\"a\"}";
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(REGISTRATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(registrationRequestWebJson)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNotNull(response.getError());
        ErrorResponse errorResponse = response.getError();
        Assert.assertEquals(SYS_TYPE_ERROR, errorResponse.getErrorTypeCode());
    }

}
