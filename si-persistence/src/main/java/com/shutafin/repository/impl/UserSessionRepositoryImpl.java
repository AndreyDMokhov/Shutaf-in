package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class UserSessionRepositoryImpl extends AbstractEntityDao<UserSession> implements UserSessionRepository {

    @Override
    public UserSession findSessionBySessionId(String sessionId) {
        return (UserSession) getSession()
                .createQuery("SELECT e FROM UserSession e where e.sessionId = :sessionId")
                .setParameter("sessionId", sessionId)
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public UserSession findSessionBySessionIdAndIsValid(String sessionId, boolean isValid) {
        return (UserSession) getSession()
                .createQuery("SELECT e FROM UserSession e WHERE e.sessionId = :sessionId AND e.isValid = :isValid")
                .setParameter("sessionId", sessionId)
                .setParameter("isValid", isValid)
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public User findUserBySessionIdAndIsValid(String sessionId, boolean isValid) {
        return (User) getSession()
                .createQuery("SELECT e.user FROM UserSession e WHERE e.sessionId = :sessionId AND e.isValid = :isValid")
                .setParameter("sessionId", sessionId)
                .setParameter("isValid", isValid)
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public UserSession findSessionBySessionIdAndInValid(String sessionId, boolean isValid) {
        return (UserSession) getSession()
                .createQuery("SELECT e FROM UserSession e WHERE e.sessionId = :sessionId AND e.isValid = :isValid")
                .setParameter("sessionId", sessionId)
                .setParameter("isValid", isValid)
                .setCacheable(true)
                .uniqueResult();
    }

   @Override
    public int updateAllValidExpiredSessions() {
        return getSession()
                .createQuery("UPDATE UserSession e SET e.isValid = false WHERE e.expirationDate < :date")
                .setParameter("date", new Date())
                .executeUpdate();
    }

    @Override
    public int deleteAllInvalidSessions(Date date) {
        return getSession()
                .createQuery("DELETE FROM UserSession e WHERE e.isValid = false AND e.expirationDate < :date")
                .setParameter("date", date)
                .executeUpdate();
    }

}
