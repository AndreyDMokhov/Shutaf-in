package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private
    UserSessionRepository userSessionRepository;

    @Override
    public void logout(String sessionId) {
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (userSession == null) {
            throw new AuthenticationException();
        }
        userSession.setValid(false);
        userSessionRepository.update(userSession);
    }
}
