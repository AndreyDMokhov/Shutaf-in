package com.shutafin.service.impl;

import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.sender.matching.UserMatchControllerSender;
import com.shutafin.sender.matching.UserMatchingScoreControllerSender;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserAccountControllerSender userAccountControllerSender;

    @Override
    public List<Long> findMatchingUsers(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        return userMatchControllerSender.getMatchingUsers(userId);
    }

    @Override
    public Map<Long, Integer> getMatchingUsersWithScores(Long userId) {
        if (userId == null) {
            return new HashMap<>();
        }
        return userMatchingScoreControllerSender.getUserMatchingScores(userId);
    }

    @Override
    public void saveQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers) {

        userMatchControllerSender.saveSelectedUserQuestionsAnswers(userId, questionsAnswers);
        //TODO access to AccountStatus.COMPLETED_REQUIRED_MATCHING
        userAccountControllerSender.updateUserAccountStatus(userId, 4); //AccountStatus.COMPLETED_REQUIRED_MATCHING
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
