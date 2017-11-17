package com.shutafin.service;



import com.shutafin.model.infrastructure.User;
import com.shutafin.model.DTO.UserQuestionAnswerDTO;
import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;
import com.shutafin.model.DTO.QuestionsListWithSelectedAnswersDTO;

import java.util.List;

public interface UserMatchService {
    List<Long> findMatchingUsers(User user);
    void saveQuestionsAnswers(User user, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(User user);
    List<QuestionsListWithSelectedAnswersDTO> getUserQuestionsSelectedAnswers(User user);
}
