package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInitializationService;
import com.shutafin.system.BaseTestImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by evgeny on 8/1/2017.
 */
@RunWith(SpringRunner.class)
public class UserInitializationControllerTest extends BaseTestImpl {
    private static final String INITIALIZATION_REQUEST_URL = "/userInitialization/init";
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
    public void userSessionUnexists(){
        Mockito.when(sessionManagementService.findValidUserSession(INVALID_SESSION)).thenReturn(null);
        List<HttpHeaders> headers = addSessionIdToHeader(INVALID_SESSION);
        APIWebResponse response = getResponse(INITIALIZATION_REQUEST_URL, HttpMethod.GET, headers);
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

        Mockito.when(sessionManagementService.findValidUserSession(VALID_SESSION)).thenReturn(userSession);
        Mockito.when(userInitializationService.getUserInitData(userSession.getUser())).thenReturn(userInit);
        List<HttpHeaders> headers = addSessionIdToHeader(VALID_SESSION);
        APIWebResponse response = getResponse(INITIALIZATION_REQUEST_URL, HttpMethod.GET, headers);
        Assert.assertNull(response.getError());
        Assert.assertNotNull(response.getData());
    }

    private List<HttpHeaders> addSessionIdToHeader(String sessionId){
        List<HttpHeaders> headers = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("session_id", sessionId);
        headers.add(httpHeaders);
        return headers;
    }

    @After
    public void tearDown(){

    }
}
