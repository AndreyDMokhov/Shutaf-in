package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.account.AccountUserImageWeb;


public interface UserAccountService {

    Integer getUserAccountStatus(User user);
    void updateUserAccountStatus(Integer accountStatusId, User user);
    UserImage updateProfileImage(AccountUserImageWeb userImageWeb, User user);
    UserImage findUserAccountProfileImage(User user);
    void deleteUserAccountProfileImage(User user);
    UserAccount checkUserAccountStatus(User user);
    UserAccount findUserAccountByUser(User user);
}
