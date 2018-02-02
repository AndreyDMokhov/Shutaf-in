package com.shutafin.controller;

import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.service.UserInfoService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserProfileControllerTest extends BaseTestImpl {

    private static final String GET_USER_INFO_URL = "/users/profile";

    @MockBean
    private UserInfoService userInfoService;

    @Before
    public void setUp(){
        Mockito.when(userInfoService.getUserInfo(Mockito.anyLong()))
                .thenReturn(new AccountUserInfoResponseDTO());
    }

    @Test
    public void getUserInfo_Positive(){
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USER_INFO_URL +"/1234")
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }
}
