package com.shutafin.service.extended;

import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;

import java.util.List;
import java.util.Map;

public interface UserQuestionExtendedAnswerService {

    Map<QuestionExtended, List<UserQuestionExtendedAnswer>> getAllUserQuestionExtendedAnswers(Long userId);
    Map<Long, Map<QuestionExtended, List<UserQuestionExtendedAnswer>>> getUserQuestionExtendedAnswersByUserIds(List<Long> userIds);
    void addUserQuestionExtendedAnswers(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList, Long userId);
    List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long userId);
    List<Long> getUsersToMatchSortedByUserAnswersWeightSum(List<Long> usersToMatch);
}
