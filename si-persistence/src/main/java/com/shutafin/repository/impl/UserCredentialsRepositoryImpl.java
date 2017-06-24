package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/19/2017.
 */
@Repository
public class UserCredentialsRepositoryImpl extends AbstractEntityDao<UserCredentials> implements UserCredentialsRepository {

    @Override
    public UserCredentials findUserByUserId(User user) {
        return (UserCredentials) getSession()
                .createQuery("FROM UserCredentials e where e.user.id = :userId")
                .setParameter("userId", user.getId()).uniqueResult();
    }
}
