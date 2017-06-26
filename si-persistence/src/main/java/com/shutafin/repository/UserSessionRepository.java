package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.base.PersistentDao;

import java.util.Date;
import java.util.List;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface UserSessionRepository extends PersistentDao<UserSession> {
    UserSession findSessionBySessionId(String sessionId);
    UserSession findSessionByUser(User user);
    List<UserSession> findAllInvalidSessions();
    List<UserSession> findAllInvalidSessions(Date date);
    void updateAllValidExpiredSessions();
}
