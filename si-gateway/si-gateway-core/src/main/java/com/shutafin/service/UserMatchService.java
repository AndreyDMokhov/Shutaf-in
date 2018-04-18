package com.shutafin.service;

import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.model.web.matching.*;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<Long> findMatchingUsers(Long userId);
    MatchedUsersSearchResponse getMatchedUserSearchResponses(Long userId, String fullName, Integer page, Integer results, FiltersWeb filtersWeb);
    List<UserBaseResponse> getMatchedUserBaseResponses(Long userId, String fullName, Integer page, Integer results, FiltersWeb filtersWeb);
    InitializationResponse saveQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
    List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(Integer languageId);
    List<MatchingQuestionsSelectedAnswersDTO> getUserQuestionsSelectedAnswers(Long userId);
    void setIsUserMatchingEnabled(Long userId, Boolean isEnabled);
}
