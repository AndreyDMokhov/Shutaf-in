package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerCityRequest;
import com.shutafin.model.web.user.QuestionAnswerRequest;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findMatchingUsers(User user);
    void saveQuestionsAnswers(User user, List<QuestionAnswerRequest> questionsAnswers);
    void saveUserQuestionsCityAnswers(User user, List<QuestionAnswerCityRequest> questionsCityAnswers);
    List<QuestionAnswersResponse> getUserQuestionsAnswers(User user);
    List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user);
}
