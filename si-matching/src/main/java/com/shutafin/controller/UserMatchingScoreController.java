package com.shutafin.controller;


import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.matching.*;
import com.shutafin.service.extended.UserMatchingScoreService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/matching/extended")
@Slf4j
public class UserMatchingScoreController {

    @Autowired
    private UserMatchingScoreService userMatchingScoreService;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MatchedUsersScoresSearchResponse getUserMatchingScores(@PathVariable("userId") Long userId,
                                                                  @RequestParam(value = "page") Integer page,
                                                                  @RequestParam(value = "results") Integer results) {

        return userMatchingScoreService.getUserMatchingScores(userId, page, results);
    }

    @PostMapping(value = "/base/{userId}")
    public List<UserBaseResponse> getMatchedUserBaseResponses(@PathVariable("userId") Long userId,
                                                              @RequestParam(value = "page") Integer page,
                                                              @RequestParam(value = "results") Integer results,
                                                              @RequestBody @Valid AccountUserFilterRequest accountUserFilterRequest,
                                                              BindingResult result) {
        log.debug("/matching/extended/base/{userId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return userMatchingScoreService.getMatchedUserSearchResponses(userId, page, results, accountUserFilterRequest).getMatchedUsersPerPage()
                .stream()
                .map(x -> UserBaseResponse.builder()
                        .userId(x.getUserId())
                        .firstName(x.getFirstName())
                        .lastName(x.getLastName())
                        .profileImage(x.getUserImage())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/search/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public MatchedUsersSearchResponse getMatchedUserSearchResponses(@PathVariable("userId") Long userId,
                                                                    @RequestParam(value = "page") Integer page,
                                                                    @RequestParam(value = "results") Integer results,
                                                                    @RequestBody @Valid AccountUserFilterRequest accountUserFilterRequest,
                                                                    BindingResult result) {
        log.debug("/matching/extended/search/{userId}");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return userMatchingScoreService.getMatchedUserSearchResponses(userId, page, results, accountUserFilterRequest);
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
