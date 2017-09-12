package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 8/10/2017.
 */
@Repository
public class UserQuestionAnswerRepositoryImpl extends AbstractEntityDao<UserQuestionAnswer> implements UserQuestionAnswerRepository {

    @Override
    public void deleteUserAnswers(User user) {
        getSession()
                .createQuery("DELETE UserQuestionAnswer uqa where uqa.user.id = :userId")
                .setParameter("userId", user.getId())
                .executeUpdate();
    }

}
