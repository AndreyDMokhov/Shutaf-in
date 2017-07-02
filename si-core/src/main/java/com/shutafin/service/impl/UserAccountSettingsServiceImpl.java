package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.UserAccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

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

    @Override
    @Transactional
    public void save(UserAccountSettingsWeb userAccountSettingsWeb, String sessionId) {
        String firstName = userAccountSettingsWeb.getFirstName();
        String lastNameWeb = userAccountSettingsWeb.getLastName();
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (!isExpirationDateAndValid(userSession)) {
            throw new AuthenticationException();
        }

        User user = userSession.getUser();
//        System.out.println(user);
        if (user.getFirstName().equals(firstName)) {
            user.setLastName(lastNameWeb);
        }else if (user.getLastName().equals(lastNameWeb)) {
            user.setFirstName(firstName);
        }else{
            user.setFirstName(firstName);
            user.setLastName(lastNameWeb);
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

        return new UserAccountSettingsWeb(user.getFirstName(), user.getLastName(), language.getLanguageNativeName());
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
