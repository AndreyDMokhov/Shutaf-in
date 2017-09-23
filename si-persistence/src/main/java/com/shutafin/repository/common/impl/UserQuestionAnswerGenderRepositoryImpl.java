package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswerGender;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserQuestionAnswerGenderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
@Repository
public class UserQuestionAnswerGenderRepositoryImpl extends AbstractEntityDao<UserQuestionAnswerGender> implements UserQuestionAnswerGenderRepository {
    @Override
    public List<Gender> getUserQuestionAnswer(User user, Question question) {
        return getSession()
                .createQuery("SELECT uqag.gender FROM UserQuestionAnswerGender uqag where uqag.user.id = :userId AND uqag.question.id = :questionId")
                .setParameter("userId", user.getId())
                .setParameter("questionId", question.getId())
                .list();
    }

    @Override
    public List<User> getAllMatchedUsers(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT uqag1.user ")
                .append(" FROM UserQuestionAnswerGender uqag1 INNER JOIN UserQuestionAnswerGender uqag2")
                .append(" ON uqag2.question.id = uqag1.question.id AND uqag2.gender.id = uqag1.gender.id AND uqag2.user.id <> uqag1.user.id")
                .append(" WHERE uqag1.user.id <> :userId ");

        return getSession()
                .createQuery(hql.toString())
                .setParameter("userId", user.getId())
                .list();
    }

    @Override
    public void deleteUserGenderAnswers(User user) {
        getSession()
                .createQuery("DELETE FROM UserQuestionAnswerGender uqag where uqag.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }
}
