package com.shutafin.core.service.impl;



import com.shutafin.core.service.LoginService;
import com.shutafin.core.service.PasswordService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.model.exception.exceptions.AccountBlockedException;
import com.shutafin.model.exception.exceptions.AccountNotConfirmedException;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.exception.exceptions.SystemException;
import com.shutafin.model.types.AccountStatus;
import com.shutafin.model.web.user.LoginWebModel;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.account.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final int AMOUNT_OF_ALLOWED_MAX_TRIES = 10;
    private static final int MAX_TRIES_FOR_MINUTES = 10;

    private UserRepository userRepository;
    private PasswordService passwordService;
    private UserAccountRepository userAccountRepository;
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    public LoginServiceImpl(
            UserLoginLogRepository userLoginLogRepository,
            UserRepository userRepository,
            PasswordService passwordService,
            UserAccountRepository userAccountRepository) {
        this.userLoginLogRepository = userLoginLogRepository;
        this.userRepository = userRepository;
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
        User user = userRepository.findByEmail(loginWeb.getEmail());
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
        Date timeNow = DateTime.now().toDate();
        Date timeDelay = DateTime.now().minusMinutes(MAX_TRIES_FOR_MINUTES).toDate();
        UserLoginLog lastSuccessLoginLog = userLoginLogRepository.findTopByIsLoginSuccessOrderByIdDesc(true);
        Long countTries = userLoginLogRepository.findAllByUserAndCreatedDateBetween(user, timeDelay, timeNow)
                .filter(log -> log.getId() > lastSuccessLoginLog.getId()).count();
        if (countTries >= AMOUNT_OF_ALLOWED_MAX_TRIES) {
            userAccount.setAccountStatus(AccountStatus.BLOCKED);
        }
    }

}
