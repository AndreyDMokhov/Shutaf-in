package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.QuestionAnswerRequest;
import com.shutafin.model.web.user.UserSearchResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserSearchService;
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

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@AuthenticatedUser User user, @RequestBody @Valid List<QuestionAnswerRequest> questionsAnswers) {
        userMatchService.saveQuestionsAnswers(user, questionsAnswers);
    }

}
