package com.shutafin.service;


import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInitializationData;

public interface UserInitializationService {
    UserInitializationData getUserInitializationData(User user);

}
