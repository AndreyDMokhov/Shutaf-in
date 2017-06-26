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
                .setParameter("sessionId", sessionId).uniqueResult();
    }

    @Override
    public UserSession findSessionByUser(User user) {
        return (UserSession) getSession()
                .createQuery("SELECT e FROM UserSession e where e.user = :user")
                .setParameter("user", user).uniqueResult();
    }

    @Override
    public List<UserSession> findAllInvalidSessions() {
        return (List<UserSession>) getSession()
                .createQuery("FROM UserSession e where e.isValid = false").list();
    }

    @Override
    public List<UserSession> findAllInvalidSessions(Date date) {
        return (List<UserSession>) getSession()
                .createQuery("FROM UserSession e where e.expirationDate > :date")
                .setParameter("date", date).list();
    }

    @Override
    public void updateAllValidExpiredSessions() {
        getSession()
                .createQuery("update UserSession e set e.isValid = false where e.isExpirable = true");
    }

}
