package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.base.PersistentDao;

import java.util.Date;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface UserSessionRepository extends PersistentDao<UserSession> {

    UserSession findSessionBySessionId(String sessionId);
    UserSession findSessionBySessionIdAndIsValid(String sessionId, boolean isValid);
    User findUserBySessionIdAndIsValid(String sessionId, boolean isValid);
    UserSession findSessionBySessionIdAndInValid(String sessionId, boolean isValid);
    int updateAllValidExpiredSessions();
    int deleteAllInvalidSessions(Date date);
}
