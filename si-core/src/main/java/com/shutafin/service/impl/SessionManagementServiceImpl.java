package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.SessionManagementService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SessionManagementServiceImpl implements SessionManagementService {

    private static final int NUMBER_DAYS_EXPIRATION = 30;
    private static final Boolean IS_FALSE = false;
    private static final Boolean IS_TRUE = true;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    @Transactional
    public String generateSession(User user) {
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setValid(IS_TRUE);
        userSession.setSessionId(UUID.randomUUID().toString());
        userSession.setExpirable(IS_FALSE);
        userSession.setExpirationDate(DateUtils.addDays(new Date(), (NUMBER_DAYS_EXPIRATION)));
        userSessionRepository.save(userSession);
        return userSession.getSessionId();
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(String sessionId) throws AuthenticationException {
        UserSession userSession = userSessionRepository.findSessionBySessionIdAndIiValid(sessionId);
        if (userSession != null){
            throw new AuthenticationException();
        }
    }

    @Override
    @Transactional
    public void invalidate(User user) {
        userSessionRepository.updateIsValidAllSessionsByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSession> findAllInvalidSessions() {
        return userSessionRepository.findAllInvalidSessions(new Date());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSession> findAllInvalidSessions(int numMinuteExperation) {
        Date date = DateUtils.addMinutes(new Date(), -numMinuteExperation);
        return userSessionRepository.findAllInvalidSessions(date);
    }

    @Override
    @Transactional
    public void invalidateAllExpiredSessions() {
        userSessionRepository.updateAllValidExpiredSessions();
    }

    @Override
    @Transactional
    public UserSession findSessionBySessionIdAndIiValid(String sessionId) {
        return userSessionRepository.findSessionBySessionId(sessionId);
    }
}
