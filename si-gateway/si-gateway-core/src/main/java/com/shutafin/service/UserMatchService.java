package com.shutafin.service;

import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<Long> findMatchingUsers(Long userId);
    List<UserSearchResponse> getMatchedUserSearchResponses(Long userId, String fullName, FiltersWeb filtersWeb);
    void saveQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(Integer languageId);
    List getUserQuestionsSelectedAnswers(Long userId);
}
