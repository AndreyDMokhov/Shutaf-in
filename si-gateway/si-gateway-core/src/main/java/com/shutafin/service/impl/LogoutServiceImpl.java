package com.shutafin.service.impl;

import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.account.UserSessionRepository;
import com.shutafin.service.LogoutService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private UserSessionRepository userSessionRepository;


    @Override
    public void logout(String sessionId) {

        UserSession userSession = userSessionRepository.findBySessionId(sessionId);

        userSessionRepository.save(userSession);
        userSessionRepository.evict(userSession);

        sessionManagementService.invalidateUserSession(sessionId);
    }
}
