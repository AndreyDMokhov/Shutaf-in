package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.model.web.user.UserImageWeb;


public interface UserAccountSettingsService {

    void updateAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user);
    void updateUserAccountImage(UserImageWeb userImageWeb, User user);
    UserImage findUserAccountImage(User user);
    void removeUserAccountImage(User user);

}
