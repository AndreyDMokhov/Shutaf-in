package com.shutafin.controller;


import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserMatchService;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserMatchControllerTest extends BaseTestImpl {

    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final String SAVE_USER_QUESTIONS_ANSWERS_URL = "/users/matching/required";
    private static final String CONFIQURE_URL = "/users/matching/configure";
    private static final String IS_ENABLE = "{\"enabled\":\"true\"}" ;
    private List<String> errorList;
    private Long validUser;
    private List<UserQuestionAnswerDTO> listUserQuestionAnswerDTO;
    private Boolean enabled;

    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private UserMatchService userMatchService;

    @Before
    public void setUp(){
        validUser = 1L;
        errorList = new ArrayList<>();
        listUserQuestionAnswerDTO = new ArrayList<>();
        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.when(userMatchService.saveQuestionsAnswers(Mockito.anyLong(), Mockito.anyListOf(UserQuestionAnswerDTO.class)))
                .thenReturn(new InitializationResponse());
        Mockito.doNothing().when(userMatchService).setIsUserMatchingEnabled(Mockito.anyLong(), Mockito.anyBoolean());
    }

    @Test
    public void saveUserQuestionsAnswers_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(SAVE_USER_QUESTIONS_ANSWERS_URL)
                .setRequestObject(listUserQuestionAnswerDTO)
                .setHttpMethod(HttpMethod.POST)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void configure_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(CONFIQURE_URL + "/path_variable?enabled=true")
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }
}
