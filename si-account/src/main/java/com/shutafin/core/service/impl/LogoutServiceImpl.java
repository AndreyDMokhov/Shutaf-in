package com.shutafin.core.service.impl;

import com.shutafin.core.service.LogoutService;
import com.shutafin.core.service.SessionManagementService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.account.UserRepository;
import com.shutafin.repository.account.UserSessionRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public void logout(String sessionId, User user) {

        UserSession userSession = userSessionRepository.findBySessionId(sessionId);
        sessionManagementService.invalidateUserSession(sessionId);
    }
}
