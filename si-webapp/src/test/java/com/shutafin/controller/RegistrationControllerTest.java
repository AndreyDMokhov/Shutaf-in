package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.RegistrationService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationControllerTest extends HelperTest {

    private static final String REGISTRATION_REQUEST_URL = "/users/registration/request";
    private static final String CONFIRM_REGISTRATION_REQUEST_URL = "/users/registration/confirmation/";

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
        Mockito.doNothing().when(registrationService).save(any(RegistrationRequestWeb.class));
        Mockito.when(registrationService.confirmRegistration(anyString())).thenReturn(new User());
        Mockito.when(sessionManagementService.generateNewSession(any(User.class))).thenReturn("648c208e-bfc8-49a7-9206-dfca5a8d9759");
        errorList = new ArrayList<>();
    }

    //@ResponseEntity in controller before vs entity class annotation with message processor
    @Ignore(value = "Comment out after APiWebResponse will be committed. Prints out User.class.")
    @Test
    public void registrationConfirmation_Positive() {
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CONFIRM_REGISTRATION_REQUEST_URL + "1a424de3-3671-420f-a8e2-ee97158f9ea2")
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNull(response.getError());
    }

    @Test
    public void registrationConfirmation_UrlWithAWhitespace() {
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
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
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
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
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
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
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
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_ExceededMinLength() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"pp\",\"lastName\":\"pp\",\"email\":\"pp@gmail.com\",\"password\":\"1234567\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_FIRST_NAME_LENGTH);
        errorList.add(INP_LAST_NAME_LENGTH);
        errorList.add(INP_PASSWORD_LENGTH);
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_IllegalEmail() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"gmail\",\"password\":\"12345678\",\"userLanguageId\":\"2\"}";
        errorList.add(INP_EMAIL_EMAIL);
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
    }

    @Test
    public void registrationRequestJson_IllegalUserLanguageId() throws Exception {
        String registrationRequestWebJson = "{\"firstName\":\"petr\",\"lastName\":\"petrovich\",\"email\":\"petr@gmail\",\"password\":\"12345678\",\"userLanguageId\":\"0\"}";
        errorList.add(INP_USER_LANGUAGE_ID_MIN);
        testControllerInputValidationError(REGISTRATION_REQUEST_URL, registrationRequestWebJson, errorList);
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
