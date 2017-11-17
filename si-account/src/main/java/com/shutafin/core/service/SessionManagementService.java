package com.shutafin.core.service;

import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;

public interface SessionManagementService {

    User findUserWithValidSession(String sessionId);
    UserSession findValidUserSession(String sessionId);
    void validate(String sessionId) throws AuthenticationException;
    String generateNewSession(User user);
    void invalidateUserSession(String sessionId);
    void invalidateAllExpiredSessions();
    void deleteAllInvalidSessions();
}
