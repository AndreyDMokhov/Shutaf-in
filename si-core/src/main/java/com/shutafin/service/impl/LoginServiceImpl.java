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
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {


    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private UserRepository userPersistence;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public User getSessionIdByEmail(LoginWebModel loginWeb) {
        User user = findUserByEmail(loginWeb);

        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        if (userAccount == null || userAccount.getAccountStatus() != AccountStatus.CONFIRMED){
            throw new AuthenticationException();
        }
        checkUserPassword(loginWeb, user);
        return user;
    }


    private void checkUserPassword(LoginWebModel loginWeb, User user) {
        if (! passwordService.isPasswordCorrect(user, loginWeb.getPassword())) {
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
