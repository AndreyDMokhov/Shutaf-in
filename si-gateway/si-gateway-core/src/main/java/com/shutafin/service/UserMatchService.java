package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findMatchingUsers(Long userId);
    void saveQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(Integer languageId);
    List getUserQuestionsSelectedAnswers(Long userId);
}
