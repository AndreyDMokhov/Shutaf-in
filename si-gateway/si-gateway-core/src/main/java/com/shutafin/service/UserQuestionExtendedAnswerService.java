package com.shutafin.service;

import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;

import java.util.List;


public interface UserQuestionExtendedAnswerService {

    void addUserQuestionExtendedAnswers(Long authenticatedUserId, List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList);
    List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long authenticatedUserId);
}
