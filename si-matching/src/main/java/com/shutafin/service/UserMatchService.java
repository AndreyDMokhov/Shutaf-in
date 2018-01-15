package com.shutafin.service;

import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;

import java.util.List;

public interface UserMatchService {
    List<Long> findMatchingUsers(Long userId);
    void saveSelectedUserQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(Integer languageId);
    List<MatchingQuestionsSelectedAnswersDTO> getSelectedUserQuestionsAnswers(Long userId);
    void setIsUserMatchingEnabled(Long userId, Boolean isEnabled);
    Boolean getIsMatchingEnabled(Long userId);
}
