package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserSessionRepository;
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

    @Override
    @Transactional
    public UserInit getAuthenticatedUser(String sessionId) {
        System.out.println(sessionId);
        if (sessionId == null) return null;
        User user = userSessionRepository.findSessionBySessionIdAndIiValid(sessionId, true).getUser();
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        int languageId = userAccount.getLanguage().getId();
        UserInit userInit = new UserInit(user.getFirstName(), user.getLastName(), languageId);
        return userInit;
    }
}
