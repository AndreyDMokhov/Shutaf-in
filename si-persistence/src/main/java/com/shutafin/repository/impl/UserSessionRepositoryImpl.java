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
    public UserSession findSessionBySessionIdAndInValid(String sessionId, boolean isValid) {
        return (UserSession) getSession()
                .createQuery("SELECT e FROM UserSession e where e.sessionId = :sessionId AND e.isValid = :isValid")
                .setParameter("sessionId", sessionId)
                .setParameter("isValid", isValid)
                .uniqueResult();
    }

    @Override
    public int updateIsValidAllSessionsByUser(User user) {
        return getSession()
                .createQuery("update UserSession e set e.isValid = false where e.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }

    @Override
    public List<UserSession> findAllInvalidSessions(Date date) {
        return (List<UserSession>) getSession()
                .createQuery("FROM UserSession e where e.expirationDate < :date")
                .setParameter("date", date)
                .list();
    }

    @Override
    public int updateAllValidExpiredSessions() {
        return getSession()
                .createQuery("update UserSession e set e.isValid = false where e.isExpirable = true")
                .executeUpdate();
    }

}
