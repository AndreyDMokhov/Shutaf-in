package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.user.UserQuestionAnswerWeb;
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
@RequestMapping("/user/match")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getMatchPartners(@AuthenticatedUser User user) {
        return userMatchService.findPartners(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@AuthenticatedUser User user, @RequestBody @Valid List<UserQuestionAnswerWeb> userQuestionsAnswers) {
        userMatchService.saveQuestionsAnswers(user, userQuestionsAnswers);
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionResponse> getUserMatchExamTemplate(@AuthenticatedUser User user) {
        return userMatchService.getUserQuestionsAnswers(user);
    }

}
