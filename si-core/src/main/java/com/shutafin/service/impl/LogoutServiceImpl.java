package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserSessionRepository;
import com.shutafin.repository.common.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void logout(String sessionId, User user) {

        UserSession userSession = userSessionRepository.findBySessionId(sessionId);

        userSessionRepository.save(userSession);
        userSessionRepository.evict(userSession);
        userAccountRepository.evict(userAccountRepository.findByUser(user));

        userRepository.evict(user);

        sessionManagementService.invalidateUserSession(sessionId);
    }
}
