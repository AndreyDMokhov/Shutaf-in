package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserExamKeyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 9/5/2017.
 */
@Repository
public class UserExamKeyRepositoryImpl extends AbstractEntityDao<UserExamKey> implements UserExamKeyRepository {
    @Override
    public void delete(User user) {
        getSession()
                .createQuery("DELETE FROM UserExamKey uek where uek.user.id = :userId")
                .setParameter("userId", user.getId())
                .executeUpdate();
    }

    @Override
    public UserExamKey getUserExamKey(User user) {
        return (UserExamKey) getSession()
                .createQuery("SELECT uek FROM UserExamKey uek where uek.user = :user")
                .setParameter("user", user)
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public List<User> getMatchedUsers(List<String> keys) {
        return getSession()
                .createQuery("SELECT uek.user FROM UserExamKey uek WHERE uek.examKeyRegExp in (:keys) ")
                .setParameter("keys", keys)
                .list();
    }
}
