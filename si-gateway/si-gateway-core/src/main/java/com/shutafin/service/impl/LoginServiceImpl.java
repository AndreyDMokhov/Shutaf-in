package com.shutafin.service.impl;


import com.shutafin.exception.exceptions.AccountBlockedException;
import com.shutafin.exception.exceptions.AccountNotConfirmedException;
import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.SystemException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.LoginService;
import com.shutafin.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final int AMOUNT_OF_ALLOWED_MAX_TRIES = 10;
    private static final int MAX_TRIES_FOR_MINUTES = 10;

    private UserRepository userPersistence;
    private PasswordService passwordService;
    private UserAccountRepository userAccountRepository;
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    public LoginServiceImpl(
            UserLoginLogRepository userLoginLogRepository,
            UserRepository userPersistence,
            PasswordService passwordService,
            UserAccountRepository userAccountRepository) {
        this.userLoginLogRepository = userLoginLogRepository;
        this.userPersistence = userPersistence;
        this.passwordService = passwordService;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional(noRollbackFor = AuthenticationException.class)
    public User getUserByLoginWebModel(LoginWebModel loginWeb) {
        User user = findUserByEmail(loginWeb);
        UserAccount userAccount = checkUserAccountStatus(user);
        checkUserPassword(loginWeb, userAccount, user);
        return user;
    }

    private User findUserByEmail(LoginWebModel loginWeb) {
        User user = userPersistence.findByEmail(loginWeb.getEmail());
        if (user == null) {
            log.warn("Users was not found by email {}", loginWeb.getEmail());
            throw new AuthenticationException();
        }
        return user;
    }

    private UserAccount checkUserAccountStatus(User user) {
        UserAccount userAccount = userAccountRepository.findByUser(user);
        if (userAccount == null) {
            String message = String.format("UserAccount for user with ID %s does not exist", user.getId());
            log.error(message, user.getId());
            throw new SystemException(message);
        }
        AccountStatus accountStatus = userAccount.getAccountStatus();
        if (accountStatus == AccountStatus.BLOCKED) {
            log.warn("UserAccount for userId {} is BLOCKED", user.getId());
            throw new AccountBlockedException();
        }
        if (accountStatus == AccountStatus.NEW) {
            log.warn("UserAccount for userId {} is not CONFIRMED", user.getId());
            throw new AccountNotConfirmedException();
        }

        return userAccount;
    }

    private void checkUserPassword(LoginWebModel loginWeb, UserAccount userAccount, User user) {
        if (!passwordService.isPasswordCorrect(user, loginWeb.getPassword())) {
            saveUserLoginLogEntry(user, false);
            countLoginFailsAndBlockAccountIfMoreThanMax(user, userAccount);
            log.warn("Password for userId {} is incorrect", user.getId());
            throw new AuthenticationException();
        }
        saveUserLoginLogEntry(user, true);
    }

    private void saveUserLoginLogEntry(User user, boolean isSuccess) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUser(user);
        userLoginLog.setIsLoginSuccess(isSuccess);
        userLoginLogRepository.save(userLoginLog);
    }

    private void countLoginFailsAndBlockAccountIfMoreThanMax(User user, UserAccount userAccount) {
        if (userLoginLogRepository.hasExceededMaxLoginTries(user, AMOUNT_OF_ALLOWED_MAX_TRIES, MAX_TRIES_FOR_MINUTES)) {
            userAccount.setAccountStatus(AccountStatus.BLOCKED);
            userAccountRepository.save(userAccount);
        }
    }

}
