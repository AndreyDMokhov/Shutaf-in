package com.shutafin.service.impl;


import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.UserSessionRepository;
import com.shutafin.service.LoginService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final Boolean IS_VALID = true;
    private static final int NUMBER_DAYS_EXPIRATION = 30;
    private static final Boolean IS_EXPIRABLE = false;

    @Autowired
    private
    UserSessionRepository userSessionRepository;
    @Autowired
    private
    UserCredentialsRepository userCredentials;
    @Autowired
    private
    UserRepository userPersistence;

    public String getSessionIdByEmail(LoginWebModel loginWeb) {
        User user = findUserByEmail(loginWeb);
        checkUserPassword(loginWeb, user);
        return generateSession(user);
    }

    private String generateSession(User user) {
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setValid(IS_VALID);
        userSession.setSessionId(UUID.randomUUID().toString());
        userSession.setExpirationDate(DateUtils.addDays(new Date(), (NUMBER_DAYS_EXPIRATION)));
        userSession.setExpirable(IS_EXPIRABLE);
        userSessionRepository.save(userSession);
        return userSession.getSessionId();
    }

    private void checkUserPassword(LoginWebModel loginWeb, User user) {
        try {
            if (!loginWeb.getPassword().equals(userCredentials.findUserByUserId(user).getPasswordHash())) {
                throw new AuthenticationException();
            }
        } catch (Exception e) {
            throw new AuthenticationException();
        }
    }

    private User findUserByEmail(LoginWebModel loginWeb) {
        User user = userPersistence.findUserByEmail(loginWeb.getEmail());
        if (user == null) {
            throw new AuthenticationException();
        }
        return user;
    }

}
