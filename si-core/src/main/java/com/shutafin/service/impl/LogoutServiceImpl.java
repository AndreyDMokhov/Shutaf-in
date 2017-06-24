package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    UserSessionRepository userSessionRepository;

    @Override
    public void logout(String sessionId) {
//      get session by Id
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
//      if empty, throw exception
        if(userSession==null)
            throw new AuthenticationException();;
//        set field "is_valid" to false
        userSession.setValid(false);
//        update session id
        userSessionRepository.update(userSession);
    }
}
