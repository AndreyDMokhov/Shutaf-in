package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.repository.account.UserAccountRepository;
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

    @Autowired
    private UserRepository userPersistence;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public User getUserByEmail(LoginWebModel loginWeb) {
        User user = findUserByEmail(loginWeb);
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        if (userAccount == null || userAccount.getAccountStatus() != AccountStatus.CONFIRMED) {
            log.warn("Authentication exception:");
            log.warn("UserId {} has null userAccount or status not equal CONFIRMED", user.getId());
            throw new AuthenticationException();
        }
        checkUserPassword(loginWeb, user);
        return user;
    }


    private void checkUserPassword(LoginWebModel loginWeb, User user) {
        if (!passwordService.isPasswordCorrect(user, loginWeb.getPassword())) {
            log.warn("Authentication exception:");
            log.warn("UserId {} has incorrect password", user.getId());
            throw new AuthenticationException();
        }
    }

    private User findUserByEmail(LoginWebModel loginWeb) {
        User user = userPersistence.findUserByEmail(loginWeb.getEmail());
        if (user == null) {
            log.warn("Authentication exception:");
            log.warn("Users was not found by email {}", loginWeb.getEmail());
            throw new AuthenticationException();
        }
        return user;
    }

}
