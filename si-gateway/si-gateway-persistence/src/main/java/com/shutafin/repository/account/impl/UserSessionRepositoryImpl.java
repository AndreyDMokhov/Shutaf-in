package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.account.UserSessionRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class UserSessionRepositoryImpl implements UserSessionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findUserIdBySessionIdAndIsValid(String sessionId, boolean isValid) {
        try {
            return (Long) entityManager.createQuery("SELECT e.userId FROM UserSession e WHERE e.sessionId = :sessionId AND e.isValid = :isValid")
                    .setParameter("sessionId", sessionId)
                    .setParameter("isValid", isValid)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public int updateAllValidExpiredUserSessions() {
        return entityManager.createQuery("UPDATE UserSession e SET e.isValid = false WHERE e.expirationDate < :date")
                .setParameter("date", new Date())
                .executeUpdate();
    }

}
