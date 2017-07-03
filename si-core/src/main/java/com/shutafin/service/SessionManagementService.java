package com.shutafin.service;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;

import java.util.List;

public interface SessionManagementService {
    String generateSession(User user);
    void validate(String sessionId) throws AuthenticationException;
    void invalidate(User user);
    List<UserSession> findAllInvalidSessions();
    List<UserSession> findAllInvalidSessions(int numDaysExperation);
    void invalidateAllExpiredSessions();
    UserSession findSessionBySessionIdAndIiValid(String sessionId);
}
