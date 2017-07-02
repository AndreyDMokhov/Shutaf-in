package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.user.UserSettingsWeb;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by usera on 6/29/2017.
 */

@Service
@Transactional
public class UserSettingsServiceImpl implements UserSettingsService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    @Transactional
    public void save(UserSettingsWeb userSettingsWeb, String sessionId) {
        String firstName = userSettingsWeb.getFirstName();
        String lastNameWeb = userSettingsWeb.getLastName();
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (!isExpirationDateAndValid(userSession)) {
            throw new AuthenticationException();
        }

        User user = userSession.getUser();
        System.out.println(user);
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
    public UserSettingsWeb get(String sessionId) {
        UserSession userSession = userSessionRepository.findSessionBySessionId(sessionId);
        if (!isExpirationDateAndValid(userSession)) {
            throw new AuthenticationException();
        }
        User user = userSession.getUser();
        return  new UserSettingsWeb(user.getFirstName(), user.getLastName()/*, user.getEmail()*/);
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
