package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.model.web.UserQuestionExtendedAnswersWeb;

import java.util.List;
import java.util.Map;


public interface UserQuestionExtendedAnswerService {

    Map<QuestionExtended, List<UserQuestionExtendedAnswer>> getAllUserQuestionAnswers(User user);
    void addUserQuestionAnswersWeb(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList, User user);
}
