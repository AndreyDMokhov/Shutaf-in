package com.shutafin.controller;

import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
@RestController
@RequestMapping("/users/match")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@AuthenticatedUser Long userId, @RequestBody @Valid List<UserQuestionAnswerDTO> questionsAnswers) {
        userMatchService.saveQuestionsAnswers(userId, questionsAnswers);
    }

}