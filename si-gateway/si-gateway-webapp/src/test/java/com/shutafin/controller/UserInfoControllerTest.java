package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.error.ErrorType;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInfoService;
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

import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class UserInfoControllerTest extends BaseTestImpl {
    private static final String INITIALIZATION_REQUEST_URL = "/users/settings/info/";
    private static final String VALID_SESSION = "e382d6ec-0e97-4c32-a1a2-8280160cd179";
    private static final String INVALID_SESSION = "";

    @MockBean
    private UserInfoService userInfoService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {

    }

    @Test
    public void userSessionDoesNotExist() {
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION)).thenReturn(null);
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(INITIALIZATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(headers)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNotNull(response.getError());
        Assert.assertEquals(response.getError().getErrorTypeCode(), ErrorType.AUTHENTICATION.getErrorCodeType());
    }

    @Test
    public void userSessionExists() {
        User user = new User();
        user.setFirstName("aaa");
        user.setLastName("bbb");
        user.setEmail("1@1.com");

        AccountUserInfoResponseDTO userInfoResponseDTO = new AccountUserInfoResponseDTO();
        userInfoResponseDTO.setFirstName("aaa");
        userInfoResponseDTO.setLastName("bbb");
        userInfoResponseDTO.setLanguageId(1);


        when(sessionManagementService.findUserWithValidSession(VALID_SESSION)).thenReturn(1L);
        when(userInfoService.getUserInfo(user.getId())).thenReturn(userInfoResponseDTO);

        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(INITIALIZATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(headers)
                .setResponseClass(AccountUserInfoResponseDTO.class)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getData());
    }

}


