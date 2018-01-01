package com.shutafin.service;

import com.shutafin.model.entities.UserSession;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.account.AccountUserWeb;

public interface SessionManagementService {

    Long findUserWithValidSession(String sessionId);
    UserSession findValidUserSession(String sessionId);
    void validate(String sessionId) throws AuthenticationException;
    String generateNewSession(AccountUserWeb accountUserWeb);
    void invalidateUserSession(String sessionId);
    void invalidateAllExpiredSessions();
    void deleteAllInvalidSessions();
}
