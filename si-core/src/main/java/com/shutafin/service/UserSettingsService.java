package com.shutafin.service;

import com.shutafin.model.web.user.UserSettingsWeb;

/**
 * Created by usera on 7/2/2017.
 */
public interface UserSettingsService {


    void save(UserSettingsWeb userSettingsWeb, String sessionId);
}
