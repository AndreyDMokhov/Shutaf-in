package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 8/10/2017.
 */
public interface UserQuestionAnswerRepository extends PersistentDao<UserQuestionAnswer> {
    List<UserQuestionAnswer> getUserQuestionAnswer(User user, Question question);
    void geleteUserQuestionAnswer(User user, Question question, Answer answer);
}
