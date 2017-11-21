package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.account.UserAccountRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Language findUserLanguage(User user) {
        StringBuilder hql = new StringBuilder()
                .append("select u.language from UserAccount u ")
                .append(" where ")
                .append(" u.user = :user ");
        return (Language) entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public void updateUserLanguage(Language language, User user) {
        entityManager
                .createQuery("UPDATE UserAccount SET language = :language WHERE user = :user")
                .setParameter("language", language)
                .setParameter("user", user)
                .executeUpdate();
    }

    @Override
    public void updateUserAccountImage(UserImage userImage, User user) {
        entityManager
                .createQuery("UPDATE UserAccount SET userImage.id = :userImageId WHERE user.id = :userId")
                .setParameter("userImageId", userImage.getId())
                .setParameter("userId", user.getId())
                .executeUpdate();
    }

    @Override
    public Long findUserAccountImageId(User user) {
        return (Long) entityManager
                .createQuery("SELECT e.userImage.id FROM UserAccount e where e.user = :user")
                .setParameter("user", user)
                .getSingleResult();
    }
}
