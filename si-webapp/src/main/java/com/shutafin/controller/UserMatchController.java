package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerCityRequest;
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
    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName, @AuthenticatedUser User user) {

        return userSearchService.userSearchByList(userMatchService.findMatchingUsers(user), fullName);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@AuthenticatedUser User user, @RequestBody @Valid List<QuestionAnswerRequest> questionsAnswers) {
        userMatchService.saveQuestionsAnswers(user, questionsAnswers);
    }

    @RequestMapping(value = "/save/city", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsCityAnswers(@AuthenticatedUser User user, @RequestBody @Valid List<QuestionAnswerCityRequest> questionsCityAnswers) {
        userMatchService.saveUserQuestionsCityAnswers(user, questionsCityAnswers);
    }

    @RequestMapping(value = "/questionnaire/initialization", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionResponse> getUserQuestionsAnswers(@AuthenticatedUser User user) {
        return userMatchService.getUserQuestionsAnswers(user);
    }

    @RequestMapping(value = "/questionnaire/answers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(@AuthenticatedUser User user) {
        return userMatchService.getUserQuestionsSelectedAnswers(user);
    }

}
