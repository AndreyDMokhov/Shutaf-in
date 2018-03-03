package com.shutafin.controller;


import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.service.extended.UserMatchingScoreService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/matching/extended")
@Validated
@Slf4j
public class UserMatchingScoreController {

    @Autowired
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<Long, Integer> getUserMatchingScores(@PathVariable("userId") Long userId,
                                                                  @NotNull(message = "INP.page.NotNull") @Min(value = 1, message = "INP.page.Min")
                                                                  @RequestParam(value = "page") Integer page,
                                                                  @NotNull(message = "INP.results.NotNull") @Min(value = 1, message = "INP.results.Min")
                                                                  @RequestParam(value = "results") Integer results
                                                    ) {
        long startTime = System.currentTimeMillis();
        Map<Long, Integer> res = userMatchingScoreService.getUserMatchingScores(userId, page, results);
        log.info("==========time:" + (System.currentTimeMillis() - startTime) + "===========");
        return res;
        //return userMatchingScoreService.getUserMatchingScores(userId);
    }

    @PostMapping(value = "/search/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchedUserSearchResponses(@PathVariable("userId") Long userId,
                                                                  @NotNull(message = "INP.page.NotNull") @Min(value = 1, message = "INP.page.Min")
                                                                  @RequestParam(value = "page") Integer page,
                                                                  @NotNull(message = "INP.results.NotNull") @Min(value = 1, message = "INP.results.Min")
                                                                      @RequestParam(value = "results") Integer results,
                                                                  @RequestBody AccountUserFilterRequest accountUserFilterRequest,
                                                                  BindingResult result) {
        log.debug("/matching/extended/search/{userId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return userMatchingScoreService.getMatchedUserSearchResponses(userId, page - 1, results, accountUserFilterRequest);
    }

    @PostMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserQuestionExtendedAnswers(@PathVariable("userId") Long userId,
                                               @RequestBody List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList) {
        userQuestionExtendedAnswerService.addUserQuestionExtendedAnswers(userQuestionExtendedAnswersWebList, userId);
    }

    @GetMapping(value = "/one/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserMatchingScoreDTO getOneMatchingScore(@PathVariable("userOrigin") Long userOrigin,
                                                    @PathVariable("userToMatch") Long userToMatch) {
        return userMatchingScoreService.getOneMatchingScore(userOrigin, userToMatch);
    }

    @GetMapping(value = "/delete/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long deleteUserMatchingScores(@PathVariable("userId") Long userId) {
        return userMatchingScoreService.deleteUserMatchingScores(userId);
    }
}
