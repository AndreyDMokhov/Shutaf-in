package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.account.AccountUserImageWeb;


public interface UserAccountService {

    AccountStatus getUserAccountStatus(User user);
    AccountStatus updateUserAccountStatus(Integer accountStatusId, User user);

    UserImage updateProfileImage(AccountUserImageWeb userImageWeb, User user);
    UserImage findUserAccountProfileImage(User user);

    UserAccount checkUserAccountStatus(User user);
    UserAccount findUserAccountByUser(User user);

    void deleteUserAccountProfileImage(User user);
}
