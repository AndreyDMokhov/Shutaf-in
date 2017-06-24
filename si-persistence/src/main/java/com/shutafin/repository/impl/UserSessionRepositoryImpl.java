package com.shutafin.repository.impl;

import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

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
}
