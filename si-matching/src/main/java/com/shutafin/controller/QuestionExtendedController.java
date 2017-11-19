package com.shutafin.controller;

import com.shutafin.model.DTO.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.DTO.QuestionImportanceDTO;
import com.shutafin.model.DTO.UserQuestionExtendedAnswersWeb;
import com.shutafin.service.extended.QuestionExtendedService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Rogov on 19.11.2017.
 */
@RestController
@RequestMapping("/extended/questions")
public class QuestionExtendedController {

    @Autowired
    private QuestionExtendedService questionExtendedService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @RequestMapping(value = "/answers/{languageId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(@PathVariable("languageId") Integer languageId){
        return questionExtendedService.getQuestionsExtendedWithAnswers(languageId);
    }

    @RequestMapping(value = "/importance/{languageId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionImportanceDTO> getQuestionImportanceList(@PathVariable("languageId") Integer languageId){
        return questionExtendedService.getQuestionImportanceList(languageId);
    }

    @RequestMapping(value = "/selected/answers/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(@PathVariable("userId") Long userId){
        return userQuestionExtendedAnswerService.getSelectedQuestionExtendedAnswers(userId);
    }
}
