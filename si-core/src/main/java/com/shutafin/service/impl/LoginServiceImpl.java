package com.shutafin.service.impl;


import com.shutafin.exception.exceptions.AccountBlockedException;
import com.shutafin.exception.exceptions.AccountNotValidatedException;
import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.account.UserSessionRepository;
import com.shutafin.service.LoginService;
import com.shutafin.service.PasswordService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final Boolean IS_VALID = true;
    private static final int NUMBER_DAYS_EXPIRATION = 30;
    private static final Boolean IS_EXPIRABLE = false;
    private static final int N_MAX_TRIES = 10;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserRepository userPersistence;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Transactional(noRollbackFor = AuthenticationException.class)
    public String getSessionIdByEmail(LoginWebModel loginWeb) {
        User user = findUserByEmail(loginWeb);
        checkUserAccountStatus(user);
        checkUserPassword(loginWeb, user);
        return generateSession(user);
    }

    private void checkUserAccountStatus(User user) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        if (userAccount == null ) {
            throw new AuthenticationException();
        }
        AccountStatus accountStatus = userAccount.getAccountStatus();
        if (accountStatus == AccountStatus.BLOCKED){
            throw new AccountBlockedException();
        }
        if (accountStatus == AccountStatus.NEW){
            throw new AccountNotValidatedException();
        }
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

    private void checkUserPassword(LoginWebModel loginWeb, User user) throws AuthenticationException {
        if (!passwordService.isPasswordCorrect(user, loginWeb.getPassword())) {
            saveUserLoginLogEntry(user, false);
            countLoginFailsAndBlockAccountIfMoreThanMax(user);
            throw new AuthenticationException();
        }
        saveUserLoginLogEntry(user, true);
    }

    private void saveUserLoginLogEntry(User user, boolean isSuccess){
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUser(user);
        userLoginLog.setLoginSuccess(isSuccess);
        userLoginLogRepository.save(userLoginLog);
    }

    private void countLoginFailsAndBlockAccountIfMoreThanMax(User user) {
        if(userLoginLogRepository.countLoginFails(user).intValue()>=N_MAX_TRIES){
            UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
            userAccount.setAccountStatus(AccountStatus.BLOCKED);
            userAccountRepository.update(userAccount);
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
