package com.shutafin.controller;

import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.extended.QuestionExtendedService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Edward Kats
 */
@RestController
@RequestMapping("/initialization")
public class MatchingInitializationController {

    private UserMatchService userMatchService;
    private QuestionExtendedService questionExtendedService;
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @Autowired
    public MatchingInitializationController(
            UserMatchService userMatchService,
            QuestionExtendedService questionExtendedService,
            UserQuestionExtendedAnswerService userQuestionExtendedAnswerService) {
        this.userMatchService = userMatchService;
        this.questionExtendedService = questionExtendedService;
        this.userQuestionExtendedAnswerService = userQuestionExtendedAnswerService;
    }

    @GetMapping(value = "/{userId}/all/{languageId}")
    public MatchingInitializationResponse getInitializationResponse(
            @PathVariable("userId") Long userId,
            @PathVariable("languageId") Integer languageId) {
        return MatchingInitializationResponse.builder()
                .questionAnswersResponses(userMatchService.getQuestionsAnswersByLanguageId(languageId))
                .selectedAnswersResponses(userMatchService.getSelectedUserQuestionsAnswers(userId))
                .questionExtendedWithAnswers(questionExtendedService.getQuestionsExtendedWithAnswers(languageId))
                .questionImportanceList(questionExtendedService.getQuestionImportanceList(languageId))
                .selectedExtendedAnswersResponses(userQuestionExtendedAnswerService.getSelectedQuestionExtendedAnswers(userId))
                .build();

    }

}
