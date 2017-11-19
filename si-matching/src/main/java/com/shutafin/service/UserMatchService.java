package com.shutafin.service;

import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;
import com.shutafin.model.DTO.QuestionsListWithSelectedAnswersDTO;
import com.shutafin.model.DTO.UserQuestionAnswerDTO;

import java.util.List;

public interface UserMatchService {
    List<Long> findMatchingUsers(Long userId);
    void saveSelectedUserQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(Integer languageId);
    List<QuestionsListWithSelectedAnswersDTO> getSelectedUserQuestionsAnswers(Long userId);
}
