package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.service.UserAccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by usera on 6/29/2017.
 */

@Service
@Transactional
public class UserAccountSettingsServiceImpl implements UserAccountSettingsService {

    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @Override
    @Transactional
    public void save(UserAccountSettingsWeb userAccountSettingsWeb, String sessionId) {
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (!isExpirationDateAndValid(userSession)) {
            throw new AuthenticationException();
        }
        User user = userSession.getUser();
        updateLanguage(user, userAccountSettingsWeb);
        if (updateFirstLastNames(user, userAccountSettingsWeb)) {
            userRepository.update(user);
        }
    }

    private boolean updateFirstLastNames(User user, UserAccountSettingsWeb userAccountSettingsWeb) {
        String firstName = userAccountSettingsWeb.getFirstName();
        String lastNameWeb = userAccountSettingsWeb.getLastName();
        if(!user.getFirstName().equals(firstName) && !user.getLastName().equals(lastNameWeb)) {
            user.setFirstName(firstName);
            user.setLastName(lastNameWeb);
            return true;
        }
        return false;
    }

    private void updateLanguage(User user, UserAccountSettingsWeb userAccountSettingsWeb) {
        String languageId = userAccountSettingsWeb.getLanguageId(); //"ru"
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        String languageDB = userAccount.getLanguage().getDescription();
        if (!languageDB.equals(languageId)){
            List<Language> languages = languageRepository.findAll();
            for (Language language: languages) {
                if(language.getDescription().equals(languageId)){
                    userAccount.setLanguage(language);
                }
            }
            userAccountRepository.update(userAccount);
        }
    }

    @Override
    @Transactional
    public UserAccountSettingsWeb get(String sessionId) {
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (!isExpirationDateAndValid(userSession)) {
            throw new AuthenticationException();
        }
        User user = userSession.getUser();
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        Language language = userAccount.getLanguage();
        return new UserAccountSettingsWeb(user.getFirstName(), user.getLastName(), language.getDescription());
    }

    private boolean isExpirationDateAndValid(UserSession userSession) {
        Date date = new Date();
        if(!date.before(userSession.getExpirationDate())) {
            return false;
        }
        if (!userSession.getValid()) {
            return false;
        }
        return true;
    }
}
