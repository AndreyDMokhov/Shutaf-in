package com.shutafin.controller;


import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
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

    @GetMapping(value = "/search/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Long> getMatchingUsers(@PathVariable("userId") Long userId) {
        return userMatchService.findMatchingUsers(userId);
    }

    @PutMapping(value = "/save/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveSelectedUserQuestionsAnswers(@PathVariable("userId") Long userId, @RequestBody @Valid List<UserQuestionAnswerDTO> questionsAnswers) {
        userMatchService.saveSelectedUserQuestionsAnswers(userId, questionsAnswers);
    }

    @GetMapping(value = "/questionnaire/initialization/{languageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(@PathVariable("languageId") Integer languageId) {
        return userMatchService.getQuestionsAnswersByLanguageId(languageId);
    }

    @GetMapping(value = "/questionnaire/answers/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MatchingQuestionsSelectedAnswersDTO> getSelectedUserQuestionsAnswers(@PathVariable("userId") Long userId) {
        return userMatchService.getSelectedUserQuestionsAnswers(userId);
    }

}
