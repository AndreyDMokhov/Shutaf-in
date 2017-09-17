package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.QuestionSelectedAnswer;
import com.shutafin.model.web.user.QuestionAnswerCityWeb;
import com.shutafin.model.web.user.QuestionAnswerWeb;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findMatchingUsers(User user);
    void saveQuestionsAnswers(User user, List<QuestionAnswerWeb> questionsAnswers);
    void saveUserQuestionsCityAnswers(User user, List<QuestionAnswerCityWeb> questionsCityAnswers);
    List<QuestionResponse> getUserQuestionsAnswers(User user);
    List<QuestionSelectedAnswer> getUserQuestionsSelectedAnswers(User user);
}
