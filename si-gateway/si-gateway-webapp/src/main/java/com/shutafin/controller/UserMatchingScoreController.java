package com.shutafin.controller;

import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchingScoreService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matching")
public class UserMatchingScoreController {

    @Autowired
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @GetMapping(value = "/extended", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<Long, Integer> getUserMatchingScores(@AuthenticatedUser Long authenticatedUserId) {
        return userMatchingScoreService.getUserMatchingScores(authenticatedUserId);
    }

    @PostMapping(value = "/extended", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserQuestionExtendedAnswers(@AuthenticatedUser Long authenticatedUserId,
                                               @RequestBody List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList) {
        userQuestionExtendedAnswerService.addUserQuestionExtendedAnswers(authenticatedUserId, userQuestionExtendedAnswersWebList);
    }
}
