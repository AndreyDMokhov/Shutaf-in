package com.shutafin.controller;


import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.service.extended.UserMatchingScoreService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/matching/extended")
public class UserMatchingScoreController {

    @Autowired
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<Long, Integer> getUserMatchingScores(@PathVariable("userId") Long userId) {
        return userMatchingScoreService.getUserMatchingScores(userId);
    }

    @PostMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserQuestionExtendedAnswers(@PathVariable("userId") Long userId,
                                               @RequestBody List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList) {
        userQuestionExtendedAnswerService.addUserQuestionExtendedAnswers(userQuestionExtendedAnswersWebList, userId);
    }
}
