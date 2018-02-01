package com.shutafin.controller;


import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserSearchService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.omg.CORBA.Any;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserSearchControllerTest extends BaseTestImpl {

    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final String GET_MATCHING_USERS_URL = "/users/search";
    private static final String GET_USER_BY_ID_URL = "/users/search/";
    private static final String FULL_NAME = "{\"name\": \"AaaaaBbbbb\"}";

    private Long validUser;

    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private UserSearchService userSearchService;

    @MockBean
    private UserMatchService userMatchService;

    @Before
    public void setUp(){
        validUser = 1L;
        List<UserSearchResponse> listUserSearchResponse = new ArrayList<>();

        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.when(userSearchService.userSearchByMap(Mockito.anyLong(), Mockito.anyMapOf(Long.class,Integer.class), Mockito.anyString(), Mockito.any(FiltersWeb.class)))
                .thenReturn(listUserSearchResponse);
        Mockito.when(userSearchService.findUserDataById(Mockito.anyLong()))
                .thenReturn(new UserSearchResponse());
//        Mockito.when(userMatchService.getMatchingUsersWithScores())

    }

    @Test
    public void getMatchingUsers_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_MATCHING_USERS_URL)
                .setHeaders(sessionHeaders)
                .setHttpMethod(HttpMethod.GET)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getUserById_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USER_BY_ID_URL + "5465665")
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

}
