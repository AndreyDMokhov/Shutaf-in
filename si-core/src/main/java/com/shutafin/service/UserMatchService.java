package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerWeb;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findMatchingUsers(User user);
    void saveQuestionsAnswers(User user, List<QuestionAnswerWeb> questionsAnswers);
    List<QuestionAnswersResponse> getUserQuestionsAnswers(User user);
    List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user);
}
