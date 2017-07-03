package com.shutafin.service;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;

import java.util.Date;
import java.util.List;

public interface SessionManagementService {

    User findUserWithValidSession(String sessionId);
    UserSession findValidUserSession(String sessionId);
    void validate(String sessionId) throws AuthenticationException;
    String generateNewSession(User user);
    void invalidateUserSession(String sessionId);
    void invalidateAllExpiredSessions();
    void deleteAllInvalidSessions();
}
