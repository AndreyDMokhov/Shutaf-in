package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswerCity;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserQuestionAnswerCityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 9/13/2017.
 */
@Repository
public class UserQuestionAnswerCityRepositoryImpl extends AbstractEntityDao<UserQuestionAnswerCity> implements UserQuestionAnswerCityRepository {
    @Override
    public List<City> getUserQuestionAnswer(User user, Question question) {
        return getSession()
                .createQuery("SELECT uqac.city FROM UserQuestionAnswerCity uqac where uqac.user.id = :userId AND uqac.question.id = :questionId")
                .setParameter("userId", user.getId())
                .setParameter("questionId", question.getId())
                .list();
    }

    @Override
    public List<User> getAllMatchedUsers(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT uqac2.user ")
                .append(" FROM UserQuestionAnswerCity uqac1 INNER JOIN UserQuestionAnswerCity uqac2")
                .append(" ON uqac2.question.id = uqac1.question.id AND uqac2.city.id = uqac1.city.id")
                .append(" WHERE uqac1.user.id = :userId ");

        return getSession()
                .createQuery(hql.toString())
                .setParameter("userId", user.getId())
                .list();
    }

    @Override
    public void deleteUserCityAnswers(User user) {
        getSession()
                .createQuery("DELETE FROM UserQuestionAnswerCity uqac where uqac.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }
}
