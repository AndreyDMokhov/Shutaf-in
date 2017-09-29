package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.QuestionAnswerWeb;
import com.shutafin.model.web.user.UserSearchResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users/match")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName, @AuthenticatedUser User user) {

        return userSearchService.userSearchByList(userMatchService.findMatchingUsers(user), fullName);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@AuthenticatedUser User user, @RequestBody @Valid List<QuestionAnswerWeb> questionsAnswers) {
        userMatchService.saveQuestionsAnswers(user, questionsAnswers);
    }

}
