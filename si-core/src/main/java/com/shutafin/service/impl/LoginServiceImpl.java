package com.shutafin.service.impl;


import com.shutafin.exception.exceptions.AccountBlockedException;
import com.shutafin.exception.exceptions.AccountNotConfirmedException;
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
import com.shutafin.service.LoginService;
import com.shutafin.service.PasswordService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final int N_MAX_TRIES = 10;
    private static final int TIME_FOR_TRIES_IN_MIN = 10;

    private UserRepository userPersistence;
    private PasswordService passwordService;
    private UserAccountRepository userAccountRepository;

    @Autowired
    public LoginServiceImpl(
            UserRepository userPersistence,
            PasswordService passwordService,
            UserAccountRepository userAccountRepository) {
        this.userPersistence = userPersistence;
        this.passwordService = passwordService;
        this.userAccountRepository = userAccountRepository;
    }

    public User getSessionIdByEmail(LoginWebModel loginWeb) {
    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Transactional(noRollbackFor = AuthenticationException.class)
    public String getSessionIdByEmail(LoginWebModel loginWeb) {
        User user = findUserByEmail(loginWeb);
        checkUserAccountStatus(user);
        checkUserPassword(loginWeb, user);
        return user;
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
            throw new AccountNotConfirmedException();
        }
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
        if(userLoginLogRepository.isLoginFailsMoreThanTries(user, N_MAX_TRIES, TIME_FOR_TRIES_IN_MIN)){
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
