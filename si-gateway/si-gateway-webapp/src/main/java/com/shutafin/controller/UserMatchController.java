package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public void saveUserQuestionsAnswers(@AuthenticatedUser User user, @RequestBody @Valid List<UserQuestionAnswerDTO> questionsAnswers) {
        userMatchService.saveQuestionsAnswers(user, questionsAnswers);
    }

}
