package com.shutafin.controller;

import com.shutafin.model.web.initialization.InitializationResponse;
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
@RequestMapping("/users/matching")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @PostMapping(value = "/required", produces = {MediaType.APPLICATION_JSON_VALUE})
    public InitializationResponse saveUserQuestionsAnswers(@AuthenticatedUser Long userId, @RequestBody @Valid List<UserQuestionAnswerDTO> questionsAnswers) {
        return userMatchService.saveQuestionsAnswers(userId, questionsAnswers);
    }

    @PutMapping(value = "/configure")
    public void configure(@AuthenticatedUser Long userId,
                          @RequestParam("enabled") Boolean isEnabled) {
        userMatchService.setIsUserMatchingEnabled(userId, isEnabled);
    }

}
