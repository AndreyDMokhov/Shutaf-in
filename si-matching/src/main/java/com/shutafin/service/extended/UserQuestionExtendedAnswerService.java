package com.shutafin.service.extended;

import com.shutafin.model.dto.UserQuestionExtendedAnswersWeb;
import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;

import java.util.List;
import java.util.Map;

public interface UserQuestionExtendedAnswerService {

    Map<QuestionExtended, List<UserQuestionExtendedAnswer>> getAllUserQuestionExtendedAnswers(Long userId);
    void addUserQuestionExtendedAnswers(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList, Long userId);
    List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long userId);
}
