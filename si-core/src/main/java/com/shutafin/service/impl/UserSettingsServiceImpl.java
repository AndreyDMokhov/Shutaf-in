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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        if (!isExpirationDate(userSession)) {
            throw new AuthenticationException();
        }

        User user = userSession.getUser();

        if (firstName == null) {
            user.setLastName(lastNameWeb);
        }if (lastNameWeb == null) {
            user.setFirstName(firstName);
        }if(firstName != null && lastNameWeb != null){
            user.setFirstName(firstName);
            user.setLastName(lastNameWeb);
        }
    }

    private boolean isExpirationDate(UserSession userSession) {
        Date date = new Date();
        if(!date.before(userSession.getExpirationDate())) {
            throw new AuthenticationException();
        }
        return true;
    }
}
