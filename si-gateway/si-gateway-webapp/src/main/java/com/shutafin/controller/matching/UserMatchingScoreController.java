package com.shutafin.controller.matching;

import com.shutafin.model.web.matching.MatchedUsersScoresSearchResponse;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchingScoreService;
import com.shutafin.service.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matching")
public class UserMatchingScoreController {

    @Autowired
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @Deprecated/*DEPRECATION CANDIDATE ALONG WITH MatchedUsersScoresSearchResponse. Reason: not in use*/
    @GetMapping(value = "/extended", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MatchedUsersScoresSearchResponse getUserMatchingScores(@AuthenticatedUser Long authenticatedUserId) {
        return userMatchingScoreService.getUserMatchingScores(authenticatedUserId);
    }

    @PostMapping(value = "/extended", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserQuestionExtendedAnswers(@AuthenticatedUser Long authenticatedUserId,
                                               @RequestBody List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList) {
        userQuestionExtendedAnswerService.addUserQuestionExtendedAnswers(authenticatedUserId, userQuestionExtendedAnswersWebList);
    }
}
