package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.account.UserSessionRepository;
import com.shutafin.service.SessionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class SessionManagementServiceImpl implements SessionManagementService {

    private static final int NUMBER_DAYS_EXPIRATION = 30;
    private static final Boolean IS_FALSE = false;
    private static final Boolean IS_TRUE = true;
    private static final int EXPIRED_SESSION_DELETE_TIME = 10;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    @Transactional(readOnly = true)
    public User findUserWithValidSession(String sessionId) {
        return userSessionRepository.findUserBySessionIdAndIsValid(sessionId, IS_TRUE);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSession findValidUserSession(String sessionId) {
        return userSessionRepository.findBySessionIdAndIsValidTrue(sessionId);
    }

    @Override
    @Transactional
    public void validate(String sessionId) throws AuthenticationException {
        UserSession userSession = findValidUserSession(sessionId);
        if (userSession == null) {
            log.warn("Authentication exception:");
            log.warn("SessionId {} was not found", sessionId);
            throw new AuthenticationException();
        }
        userSession.setExpirationDate(DateUtils.addDays(new Date(), NUMBER_DAYS_EXPIRATION));
        userSessionRepository.save(userSession);
    }

    @Override
    @Transactional
    public String generateNewSession(User user) {
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setIsValid(IS_TRUE);
        userSession.setSessionId(UUID.randomUUID().toString());
        userSession.setIsExpirable(IS_FALSE);
        userSession.setExpirationDate(DateUtils.addDays(new Date(), NUMBER_DAYS_EXPIRATION));
        userSessionRepository.save(userSession);
        return userSession.getSessionId();
    }

    @Override
    @Transactional
    public void invalidateUserSession(String sessionId) {
        UserSession userSession = userSessionRepository.findBySessionIdAndIsValidTrue(sessionId);
        if (userSession != null) {
            userSession.setIsValid(IS_FALSE);
            userSessionRepository.save(userSession);
        }
    }

    @Override
    @Transactional
    public void invalidateAllExpiredSessions() {
        userSessionRepository.updateAllValidExpiredUserSessions();
    }

    @Override
    @Transactional
    public void deleteAllInvalidSessions() {
        Date date = DateUtils.addMinutes(new Date(), -(EXPIRED_SESSION_DELETE_TIME));
        userSessionRepository.deleteAllByIsValidFalseAndExpirationDateAfter(date);
    }
}
