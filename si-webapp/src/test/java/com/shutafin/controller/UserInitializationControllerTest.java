package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInitializationService;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by evgeny on 8/1/2017.
 */
@RunWith(SpringRunner.class)
public class UserInitializationControllerTest extends BaseTestImpl {
    private static final String INITIALIZATION_REQUEST_URL = "/initialization/user/init";
    private static final String VALID_SESSION = "e382d6ec-0e97-4c32-a1a2-8280160cd179";
    private static final String INVALID_SESSION = "";

    @MockBean
    private UserInitializationService userInitializationService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp(){

    }

    @Test
    public void userSessionDoesNotExist(){
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

    @Ignore
    @Test
    public void userSessionExists(){
        User user = new User();
        user.setFirstName("aaa");
        user.setLastName("bbb");
        user.setEmail("1@1.com");

        UserInit userInit = new UserInit();
        userInit.setFirstName("aaa");
        userInit.setLastName("bbb");
        userInit.setLanguageId(1);

        UserSession userSession = new UserSession();
        userSession.setUser(user);

        when(sessionManagementService.findValidUserSession(VALID_SESSION)).thenReturn(userSession);
        when(userInitializationService.getUserInitData(userSession.getUser())).thenReturn(userInit);

        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(INITIALIZATION_REQUEST_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(headers)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getData());
    }

}