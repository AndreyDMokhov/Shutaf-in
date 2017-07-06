package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class UserAccountRepositoryImpl extends AbstractEntityDao<UserAccount> implements UserAccountRepository {
    @Override
    public Language getUserLanguage(User user) {
        return (Language) getSession()
                .createQuery("SELECT u.language FROM UserAccount u WHERE u.user = :user")
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public void updateUserLanguage(Integer languageId, User user) {
        getSession()
                .createQuery("UPDATE UserAccount SET language_id = :languageId WHERE user_id = :userId")
                .setParameter("languageId", languageId)
                .setParameter("userId", user.getId())
        .executeUpdate();
    }
}
