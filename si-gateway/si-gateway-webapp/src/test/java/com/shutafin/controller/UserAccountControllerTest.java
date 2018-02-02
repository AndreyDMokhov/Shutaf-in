package com.shutafin.controller;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.error.errors.InputValidationError;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserLanguageService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
public class UserAccountControllerTest extends BaseTestImpl {

    private static final String USER_SETTINGS_LANGUAGE_URL = "/users/settings/language";
    private static final String GET_USER_INFO_URL = "/users/settings/info";
    private static final String VALID_JSON = "{\"id\":\"1\"}";
    private static final String LANG_ID_0 = "{\"id\":\"0\"}";
    private static final String LANG_ID_NULL = "{\"id\":\"\"}";
    private static final String VALID_SESSION = "40042cd8-51d0-4282-b431-36ee7f6dcaef";
    private static final String INVALID_SESSION = "";
    private static final String SESSION_ID_HEADER_NAME = "session_id";
    private ArrayList<String> expectedError;
    private AccountUserInfoRequest accountUserInfoRequest;

    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private UserLanguageService userLanguageService;

    @MockBean
    private UserInfoService userInfoService;

    @Before
    public void setUp() {
        expectedError = new ArrayList<>();
        accountUserInfoRequest = createAccountUserInfoRequest();
        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION)).thenReturn(1L);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION))
                .thenThrow(new AuthenticationException());
        Mockito.doNothing().when(userLanguageService).updateUserLanguage(Mockito.any(AccountUserLanguageWeb.class), Mockito.any(Long.class));
        Mockito.when(userInfoService.getUserInfo(Mockito.anyLong()))
                .thenReturn(new AccountUserInfoResponseDTO());
        Mockito.when(userInfoService.updateUserInfo(Mockito.any(AccountUserInfoRequest.class), Mockito.anyLong()))
                .thenReturn(new InitializationResponse());
    }

    private AccountUserInfoRequest createAccountUserInfoRequest() {
        AccountUserInfoRequest accountUserInfoRequest = new AccountUserInfoRequest();
        accountUserInfoRequest.setFirstName("Shlomo");
        accountUserInfoRequest.setLastName("Shlomo");
        accountUserInfoRequest.setEmail("aaa@aaa");
        return accountUserInfoRequest;
    }

    @Test
    public void getUserInfo_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USER_INFO_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(headers)
                .setResponseClass(AccountUserInfoResponseDTO.class)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNull(response.getError());
    }

    @Test
    public void updateUserInfo_Positive(){
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USER_INFO_URL)
                .setHttpMethod(HttpMethod.POST)
                .setRequestObject(accountUserInfoRequest)
                .setHeaders(headers)
                .setResponseClass(InitializationResponse.class)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNull(response.getError());
    }

    @Test
    public void update_Positive() {
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_SETTINGS_LANGUAGE_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(VALID_JSON)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNull(response.getError());
    }

    @Test
    public void update_LangId_0() {
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_SETTINGS_LANGUAGE_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setRequestObject(new AccountUserLanguageWeb(0))
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        expectedError.add("INP.id.Min");
        Assert.assertEquals(inputValidationError.getErrors(), expectedError);
    }

    @Test
    public void update_LangIdIsNull() {
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_SETTINGS_LANGUAGE_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(LANG_ID_NULL)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        expectedError.add("INP.id.NotNull");
        Assert.assertEquals(inputValidationError.getErrors(), expectedError);
    }

    @Test
    public void update_WrongSession() {
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(USER_SETTINGS_LANGUAGE_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(headers)
                .setJsonContext(VALID_JSON)
                .build();
        APIWebResponse response = getResponse(request);

        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }
    private LanguageWeb createLanguage() {
        LanguageWeb language = new LanguageWeb();
        language.setIsActive(true);
        language.setId(1);
        language.setLanguageNativeName("Русский");
        language.setDescription("ru");
        return language;
    }
}
