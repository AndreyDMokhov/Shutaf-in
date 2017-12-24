package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.sender.matching.UserMatchControllerSender;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Transactional
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private UserMatchControllerSender userMatchControllerSender;

    @Override
    @Transactional(readOnly = true)
    //todo MS-MATCHING
    public List<User> findMatchingUsers(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        List<Long> matchingUsers = userMatchControllerSender.getMatchingUsers(userId);


        return null;
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
