package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.QuestionWeb;
import com.shutafin.model.web.user.UserQuestionAnswerWeb;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findPartners(User user);
    void saveQuestionsAnswers(User user, List<UserQuestionAnswerWeb> userQuestionsAnswers);
    List<QuestionWeb> getUserQuestionsAnswers(User user);
}
