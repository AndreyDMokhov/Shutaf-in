package com.shutafin.service;

import com.shutafin.model.web.user.UserAccountSettingsWeb;

/**
 * Created by usera on 7/2/2017.
 */
public interface UserAccountSettingsService {


    void save(UserAccountSettingsWeb userAccountSettingsWeb, String sessionId);

    UserAccountSettingsWeb get (String sessionId);
}
