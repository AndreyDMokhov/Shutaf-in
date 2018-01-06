package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.account.AccountUserImageWeb;


public interface UserAccountService {

    UserImage updateProfileImage(AccountUserImageWeb userImageWeb, User user);
    UserImage findUserAccountProfileImage(User user);
    void deleteUserAccountProfileImage(User user);
    void checkUserAccountStatus(User user);
    UserAccount findUserAccountByUser(User user);
}
