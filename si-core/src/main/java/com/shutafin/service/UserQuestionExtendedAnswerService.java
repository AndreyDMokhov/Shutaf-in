package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.model.web.UserQuestionExtendedAnswersWeb;

import java.util.List;
import java.util.Map;


public interface UserQuestionExtendedAnswerService {

    void addUserQuestionAnswer(UserQuestionExtendedAnswer userQuestionExtendedAnswer);
    Map<QuestionExtended, UserQuestionExtendedAnswer> getAllUserQuestionAnswers(User user);
    void updateUserQuestionAnswer(UserQuestionExtendedAnswer userQuestionExtendedAnswer);
    void addUserQuestionAnswersWeb(List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList, User user);
}
