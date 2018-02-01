package com.shutafin.controller;


import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserMatchingScoreService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserMatchingScoreControllerTest extends BaseTestImpl{

    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final String GET_USER_MATCHING_SCORES_URL = "/matching/extended";

    private Long validUser;
    List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList;

    @MockBean
    private UserMatchingScoreService userMatchingScoreService;

    @MockBean
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @MockBean
    private SessionManagementService sessionManagementService;

    @Before
    public void setUp(){
        validUser = 1L;
        userQuestionExtendedAnswersWebList = new ArrayList<>();
        Map<Long, Integer> longIntegerMap = new HashMap<>();

        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.when(userMatchingScoreService.getUserMatchingScores(Mockito.anyLong()))
                .thenReturn(longIntegerMap);
    }

    @Test
    public void getUserMatchingScores_Positive() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USER_MATCHING_SCORES_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .setResponseClass(Map.class)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void addUserQuestionExtendedAnswers_Positive() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USER_MATCHING_SCORES_URL)
                .setHttpMethod(HttpMethod.POST)
                .setHeaders(sessionHeaders)
                .setRequestObject(userQuestionExtendedAnswersWebList)
                .build();
        APIWebResponse apiResponse = getResponse(request);
        Assert.assertNull(apiResponse.getError());
    }
}
