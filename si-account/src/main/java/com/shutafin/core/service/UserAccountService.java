package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;


public interface UserAccountService {

    UserImage updateProfileImage(UserImageWeb userImageWeb, User user);
    UserImage findUserAccountProfileImage(User user);
    void deleteUserAccountProfileImage(User user);
    UserAccount checkUserAccountStatus(User user);
}
