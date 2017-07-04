package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class UserAccountRepositoryImpl extends AbstractEntityDao<UserAccount> implements UserAccountRepository {
    @Override
    public UserAccount getAccountUserByUserId(User user) {
        return (UserAccount) getSession()
                .createQuery("FROM UserAccount e where e.user.id = :userId")
                .setParameter("userId", user.getId()).uniqueResult();
    }
}
