package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Question;

import java.util.Map;

public interface UserQuestionAnswerService {

    void addUserQuestionAnswer(UserQuestionAnswer userQuestionAnswer);
    Map<Question, UserQuestionAnswer> getAllUserQuestionAnswers(User user);
    void updateUserQuestionAnswer(UserQuestionAnswer userQuestionAnswer);
}
