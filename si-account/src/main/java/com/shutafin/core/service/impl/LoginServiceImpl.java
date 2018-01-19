package com.shutafin.core.service.impl;

import com.shutafin.core.service.LoginService;
import com.shutafin.core.service.PasswordService;
import com.shutafin.core.service.UserAccountService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.account.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final int AMOUNT_OF_ALLOWED_MAX_TRIES = 10;
    private static final int MAX_TRIES_FOR_MINUTES = 10;

    private UserRepository userRepository;
    private PasswordService passwordService;
    private UserAccountService userAccountService;
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    public LoginServiceImpl(
            UserLoginLogRepository userLoginLogRepository,
            UserRepository userRepository,
            PasswordService passwordService,
            UserAccountService userAccountService) {
        this.userLoginLogRepository = userLoginLogRepository;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.userAccountService = userAccountService;
    }

    @Transactional(noRollbackFor = AuthenticationException.class)
    public AccountUserWeb getUserByLoginWebModel(AccountLoginRequest loginWeb) {
        User user = findUserByEmail(loginWeb);
        UserAccount userAccount = userAccountService.findUserAccountByUser(user);
        checkPassword(loginWeb, userAccount);
        userAccountService.checkUserAccountStatus(user);
        return new AccountUserWeb(
                user.getId(),
                user.getLastName(),
                user.getFirstName());
    }

    @Transactional(noRollbackFor = AuthenticationException.class)
    public void checkUserPassword(AccountLoginRequest loginWeb) {
        User user = findUserByEmail(loginWeb);
        UserAccount userAccount = userAccountService.findUserAccountByUser(user);
        checkPassword(loginWeb, userAccount);
    }

    private User findUserByEmail(AccountLoginRequest loginWeb) {
        User user = userRepository.findByEmail(loginWeb.getEmail());
        if (user == null) {
            log.warn("Users was not found by email {}", loginWeb.getEmail());
            throw new AuthenticationException();
        }
        return user;
    }

    private void checkPassword(AccountLoginRequest loginWeb, UserAccount userAccount) {
        if (!passwordService.isPasswordCorrect(userAccount.getUser(), loginWeb.getPassword())) {
            saveUserLoginLogEntry(userAccount.getUser(), false);
            countLoginFailsAndBlockAccountIfMoreThanMax(userAccount);
            log.warn("Password for userId {} is incorrect", userAccount.getUser().getId());
            throw new AuthenticationException();
        }
        saveUserLoginLogEntry(userAccount.getUser(), true);
    }

    private void saveUserLoginLogEntry(User user, boolean isSuccess) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUser(user);
        userLoginLog.setIsLoginSuccess(isSuccess);
        userLoginLogRepository.save(userLoginLog);
    }

    private void countLoginFailsAndBlockAccountIfMoreThanMax(UserAccount userAccount) {
        Date timeNow = DateTime.now().toDate();
        Date timeDelay = DateTime.now().minusMinutes(MAX_TRIES_FOR_MINUTES).toDate();
        UserLoginLog lastSuccessLoginLog = userLoginLogRepository.findTopByIsLoginSuccessOrderByIdDesc(true);
        Long countTries = userLoginLogRepository
                .findAllByUserAndCreatedDateBetween(userAccount.getUser(), timeDelay, timeNow)
                .filter(log -> log.getId() > lastSuccessLoginLog.getId())
                .count();
        if (countTries >= AMOUNT_OF_ALLOWED_MAX_TRIES) {
            userAccount.setAccountStatus(AccountStatus.BLOCKED);
        }
    }

}
