package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
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
    public Language findUserLanguage(User user) {
        return (Language) getSession()
                .createQuery("SELECT u.language FROM UserAccount u WHERE u.user = :user")
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public void updateUserLanguage(Integer languageId, User user) {
        getSession()
                .createQuery("UPDATE UserAccount SET language.id = :languageId WHERE user.id = :userId")
                .setParameter("languageId", languageId)
                .setParameter("userId", user.getId())
        .executeUpdate();
    }
    @Override
    public UserAccount findUserAccountByUser(User user) {
        return (UserAccount) getSession()
                .createQuery("SELECT e FROM UserAccount e where e.user = :user")
                .setParameter("user", user).uniqueResult();
    }

    @Override
    public void updateUserAccountImage(UserImage userImage, User user) {
        getSession()
                .createQuery("UPDATE UserAccount SET userImage.id = :userImageId WHERE user.id = :userId")
                .setParameter("userImageId", userImage.getId())
                .setParameter("userId", user.getId())
                .executeUpdate();
    }

    @Override
    public UserImage findUserAccountImage(User user) {
        return (UserImage) getSession()
                .createQuery("SELECT e.userImage FROM UserAccount e where e.user = :user")
                .setParameter("user", user).uniqueResult();
    }
}
