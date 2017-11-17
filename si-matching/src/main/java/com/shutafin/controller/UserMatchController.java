package com.shutafin.controller;


import com.shutafin.model.infrastructure.User;
import com.shutafin.model.DTO.UserQuestionAnswerDTO;
import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;
import com.shutafin.model.DTO.QuestionsListWithSelectedAnswersDTO;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user/match")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @RequestMapping(value = "/search/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Long> getMatchingUsers(@PathVariable("userId") Long userId) {
        return userMatchService.findMatchingUsers(new User(userId));
    }

    @RequestMapping(value = "/save/{userId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@PathVariable("userId") Long userId, @RequestBody @Valid List<UserQuestionAnswerDTO> questionsAnswers) {
        userMatchService.saveQuestionsAnswers(new User(userId), questionsAnswers);
    }

    @RequestMapping(value = "/questionnaire/initialization/{userId}/{languageId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(@PathVariable("userId") Long userId, @PathVariable("languageId") Integer languageId) {
        return userMatchService.getUserQuestionsAnswers(new User(userId, languageId));
    }

    @RequestMapping(value = "/questionnaire/answers/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionsListWithSelectedAnswersDTO> getUserQuestionsSelectedAnswers(@PathVariable("userId") Long userId) {
        return userMatchService.getUserQuestionsSelectedAnswers(new User(userId));
    }

}
