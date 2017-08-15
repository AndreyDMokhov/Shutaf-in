package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.model.web.user.UserImageWeb;


public interface UserAccountService {

    void updateAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user);
    void updateProfileImage(UserImageWeb userImageWeb, User user);
    UserImage findUserAccountProfileImage(User user);
    void deleteUserAccountProfileImage(User user);

}
