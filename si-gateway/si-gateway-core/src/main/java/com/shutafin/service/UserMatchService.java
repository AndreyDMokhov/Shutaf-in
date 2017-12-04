package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findMatchingUsers(User user);
    void saveQuestionsAnswers(User user, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(Language language);
    List getUserQuestionsSelectedAnswers(User user);
}
