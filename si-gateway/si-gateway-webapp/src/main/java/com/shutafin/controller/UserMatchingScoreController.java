package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.UserQuestionExtendedAnswersWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchingScoreService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@Deprecated
@RestController
@RequestMapping("/matching/extended")
public class UserMatchingScoreController {

    @Autowired
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<Long, Integer> getUserMatchingScores(@AuthenticatedUser User user) {
        return userMatchingScoreService.getUserMatchingScores(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserQuestionExtendedAnswers(@AuthenticatedUser User user,
                                               @RequestBody List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList) {
        userQuestionExtendedAnswerService.addUserQuestionExtendedAnswers(userQuestionExtendedAnswersWebList, user);
    }
}
