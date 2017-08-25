package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 8/10/2017.
 */
@Repository
public class UserQuestionAnswerRepositoryImpl extends AbstractEntityDao<UserQuestionAnswer> implements UserQuestionAnswerRepository {
    @Override
    public List<UserQuestionAnswer> getUserQuestionAnswer(User user, Question question) {
        return getSession()
                .createQuery("SELECT uqa FROM UserQuestionAnswer uqa where uqa.user.id = :userId AND uqa.question.id = :questionId")
                .setParameter("userId", user.getId())
                .setParameter("questionId", question.getId())
                .list();
    }

    @Override
    public List<UserQuestionAnswer> getAllUserQuestionAnswers(User user) {
        return getSession()
                .createQuery("SELECT uqa FROM UserQuestionAnswer uqa where uqa.user.id = :userId")
                .setParameter("userId", user.getId())
                .list();
    }
}
