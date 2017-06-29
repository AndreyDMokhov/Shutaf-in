package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.UserLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by evgeny on 6/26/2017.
 */
@Service
@javax.transaction.Transactional
public class UserLanguageServiceImpl implements UserLanguageService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public Language get(String sessionId) {
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (userSession != null) {
            return userAccountRepository.getUserLanguage(userSession.getUser());
        }
        return null;
    }

    @Override
    public void update(UserLanguageWeb userLanguageWeb, String sessionId) {
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (userSession != null)
            update(userLanguageWeb, userSession.getUser());
    }

    @Override
    public void update(UserLanguageWeb userLanguageWeb, User user) {
        userAccountRepository.updateUserLanguage(userLanguageWeb.getLanguageId(), user);
    }

    @Override
    @Transactional
    public UserLanguageWeb findById(Long id) {
        UserAccount userAccount = userAccountRepository.findById(id);
        UserLanguageWeb userLanguageWeb = null;
        if (userAccount !=null){
            userLanguageWeb = new UserLanguageWeb();
        }
        return userLanguageWeb;
    }

}
