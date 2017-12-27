package com.shutafin.controller;

import com.shutafin.model.web.matching.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.matching.QuestionImportanceDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.service.extended.QuestionExtendedService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Rogov on 19.11.2017.
 */
@RestController
@RequestMapping("/matching/extended/questions")
public class QuestionExtendedController {

    @Autowired
    private QuestionExtendedService questionExtendedService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @GetMapping(value = "/answers/{languageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(@PathVariable("languageId") Integer languageId){
        return questionExtendedService.getQuestionsExtendedWithAnswers(languageId);
    }

    @GetMapping(value = "/importance/{languageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionImportanceDTO> getQuestionImportanceList(@PathVariable("languageId") Integer languageId){
        return questionExtendedService.getQuestionImportanceList(languageId);
    }

    @GetMapping(value = "/selected/answers/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(@PathVariable("userId") Long userId){
        return userQuestionExtendedAnswerService.getSelectedQuestionExtendedAnswers(userId);
    }
}
