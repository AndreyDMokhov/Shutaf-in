package com.shutafin.service;

import com.shutafin.model.dto.QuestionsListWithAnswersDTO;
import com.shutafin.model.dto.QuestionsListWithSelectedAnswersDTO;
import com.shutafin.model.dto.UserQuestionAnswerDTO;

import java.util.List;

public interface UserMatchService {
    List<Long> findMatchingUsers(Long userId);
    void saveSelectedUserQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(Integer languageId);
    List<QuestionsListWithSelectedAnswersDTO> getSelectedUserQuestionsAnswers(Long userId);
}
