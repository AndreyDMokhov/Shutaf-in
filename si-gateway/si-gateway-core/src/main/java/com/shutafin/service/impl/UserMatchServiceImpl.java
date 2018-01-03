package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.sender.matching.UserMatchControllerSender;
import com.shutafin.sender.matching.UserMatchingScoreControllerSender;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private UserMatchControllerSender userMatchControllerSender;

    @Autowired
    private UserMatchingScoreControllerSender userMatchingScoreControllerSender;

    @Override
    public List<Long> findMatchingUsers(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        return userMatchControllerSender.getMatchingUsers(userId);
    }

    @Override
    public List<UserSearchResponse> getMatchedUserSearchResponses(Long userId, String fullName, FiltersWeb filtersWeb) {
        if (userId == null) {
            return new ArrayList<>();
        }
        return userMatchingScoreControllerSender.getMatchedUserSearchResponses(userId, new AccountUserFilterRequest(null, fullName, filtersWeb));
    }

    @Override
    public void saveQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers) {

        userMatchControllerSender.saveSelectedUserQuestionsAnswers(userId, questionsAnswers);
    }

    @Override
    public List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(Integer languageId) {
        return userMatchControllerSender.getQuestionsAnswersByLanguageId(languageId);
    }

    @Override
    public List<MatchingQuestionsSelectedAnswersDTO> getUserQuestionsSelectedAnswers(Long userId) {
        return userMatchControllerSender.getSelectedUserQuestionsAnswers(userId);
    }
}
