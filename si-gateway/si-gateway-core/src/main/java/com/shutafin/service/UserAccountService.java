package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserImageWeb;


public interface UserAccountService {

    AccountUserImageWeb updateProfileImage(AccountUserImageWeb userImageWeb, User user);
    AccountUserImageWeb findUserAccountProfileImage(User user);
    void deleteUserAccountProfileImage(User user);

}
