package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.matching.QuestionAnswer;
import com.shutafin.model.web.QuestionWeb;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.model.web.initialization.QuestionResponseDTO;
import com.shutafin.model.web.user.UserQuestionAnswerWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 8/12/2017.
 */
@RestController
@RequestMapping("/user/match")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private SessionManagementService sessionManagementService;


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getMatchPartners(@RequestHeader(value = "session_id") String sessionId) {
        return userMatchService.findPartners(sessionManagementService.findUserWithValidSession(sessionId));
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUserQuestionsAnswers(@RequestHeader(value = "session_id") String sessionId, @RequestBody @Valid List<UserQuestionAnswerWeb> userQuestionsAnswers) {
        userMatchService.saveQuestionsAnswers(sessionManagementService.findUserWithValidSession(sessionId), userQuestionsAnswers);
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuestionWeb> getUserMatchExamTemplate(@RequestHeader(value = "session_id") String sessionId) {
        return userMatchService.getUserQuestionsAnswers(sessionManagementService.findUserWithValidSession(sessionId));
    }

}
