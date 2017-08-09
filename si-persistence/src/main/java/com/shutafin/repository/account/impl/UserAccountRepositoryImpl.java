package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class UserAccountRepositoryImpl extends AbstractEntityDao<UserAccount> implements UserAccountRepository {


    @Override
    public Language findUserLanguage(User user) {
        StringBuilder hql = new StringBuilder();
        hql.append("select l from UserAccount u, Language l ");
        hql.append(" where u.language = l.id ");
        hql.append(" and ");
        hql.append(" u.user = :user ");
        return (Language) getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public void updateUserLanguage(Language language, User user) {
        getSession()
                .createQuery("UPDATE UserAccount SET language = :language WHERE user = :user")
                .setParameter("language", language)
                .setParameter("user", user)
        .executeUpdate();
    }
    @Override
    public UserAccount findUserAccountByUser(User user) {
        return (UserAccount) getSession()
                .createQuery("SELECT e FROM UserAccount e where e.user = :user")
                .setParameter("user", user).uniqueResult();
    }
}
