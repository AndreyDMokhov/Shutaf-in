package com.shutafin.controller;


import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;
import com.shutafin.model.DTO.QuestionsListWithSelectedAnswersDTO;
import com.shutafin.model.DTO.UserQuestionAnswerDTO;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/matching")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @RequestMapping(value = "/search/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Long> getMatchingUsers(@PathVariable("userId") Long userId) {
        return userMatchService.findMatchingUsers(userId);
    }

    @RequestMapping(value = "/save/{userId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveSelectedUserQuestionsAnswers(@PathVariable("userId") Long userId, @RequestBody @Valid List<UserQuestionAnswerDTO> questionsAnswers) {
        userMatchService.saveSelectedUserQuestionsAnswers(userId, questionsAnswers);
    }

    @RequestMapping(value = "/questionnaire/initialization/{languageId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(@PathVariable("languageId") Integer languageId) {
        return userMatchService.getQuestionsAnswersByLanguageId(languageId);
    }

    @RequestMapping(value = "/questionnaire/answers/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionsListWithSelectedAnswersDTO> getSelectedUserQuestionsAnswers(@PathVariable("userId") Long userId) {
        return userMatchService.getSelectedUserQuestionsAnswers(userId);
    }

}
