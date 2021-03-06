package com.shutafin.controller;

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


@RunWith(SpringRunner.class)
public class UserInfoControllerTest extends BaseTestImpl {
    private static final String INITIALIZATION_REQUEST_URL = "/users/settings/info/";
    private static final String VALID_SESSION = "e382d6ec-0e97-4c32-a1a2-8280160cd179";
    private static final String INVALID_SESSION = "";

    private AccountUserInfoResponseDTO userInfoResponseDTO = AccountUserInfoResponseDTO
            .builder()
            .firstName("aaa")
            .lastName("bbb")
            .languageId(1)
            .userId(1L)
            .build();

    @MockBean
    private UserInfoService userInfoService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp() {
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION)).thenReturn(null);
        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION)).thenReturn(userInfoResponseDTO.getUserId());
    }

    @Test
    public void userSessionDoesNotExist() {
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

        Mockito.when(userInfoService.getUserInfo(userInfoResponseDTO.getUserId())).thenReturn(userInfoResponseDTO);

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


