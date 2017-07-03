package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.repository.custom.UserInitializationRepository;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserInitializationServiceImpl implements UserInitializationService {
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserInitializationRepository userInitializationRepository;

    @Override
    @Transactional
    public UserInit getUserInitData(User user) {

        UserInit userInit = userInitializationRepository.getUserInitializationData(user);
        return userInit;
    }
}
