package com.shutafin.service;

import com.shutafin.model.web.user.UserSettingsWeb;

/**
 * Created by usera on 6/29/2017.
 */
public interface UserSettingsService {

    void save (UserSettingsWeb userSettingsWeb, String sessionId);
}
