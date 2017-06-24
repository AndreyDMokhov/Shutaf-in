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

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional

public class LoginServiceImpl implements LoginService {

    private static final Boolean IS_VALID = true;
    private static final int NUMBER_DAYS_EXPIRATION = 30;
    private static final Boolean IS_EXPIRABLE = false;

    @Autowired
    UserSessionRepository userSessionRepository;
    @Autowired
    UserCredentialsRepository userCredentials;
    @Autowired
    UserRepository userPersistence;

    public String login(LoginWebModel loginWeb) {
//        find user by email
        User user = findUserByEmail(loginWeb);
//        check password
        checkUserPassword(loginWeb, user);
//        get new session with id
        return generateSession(user);
    }

    private String generateSession(User user) {
        UserSession userSession = new UserSession();
        userSession.setUser(user);
//        set session as valid
        userSession.setValid(IS_VALID);
//        get new session id
        userSession.setSessionId(UUID.randomUUID().toString());
//        get expiration date
        userSession.setExpirationDate(DateUtils.addDays(new Date(), (NUMBER_DAYS_EXPIRATION)));
        userSession.setExpirable(IS_EXPIRABLE);
//        get table id
        Long userSessionId = (Long) userSessionRepository.save(userSession);
        userSession.setId(userSessionId);
//        return session id direct to web
        return userSession.getSessionId();
    }

    private void checkUserPassword(LoginWebModel loginWeb, User user) {
//       check password, if not correct, throw exception.
        try{
        if (!loginWeb.getPassword().equals(userCredentials.findById(user.getId()).getPasswordHash())) {
            throw new AuthenticationException();
        }}
        catch(Exception e){
            throw new AuthenticationException();
        }
    }

    private User findUserByEmail(LoginWebModel loginWeb) {
//        find user by email
        User user = userPersistence.findUserByEmail(loginWeb.getEmail());
//        check if user exist, if not, throw exception
        if (user == null)
            throw new AuthenticationException();
        return user;
    }

}
