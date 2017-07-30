package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserAccountSettingsWeb;


public interface UserAccountSettingsService {

    void updateAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user);

}
