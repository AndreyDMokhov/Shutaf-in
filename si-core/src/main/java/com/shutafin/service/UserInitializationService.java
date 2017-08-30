package com.shutafin.service;


import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponse;

public interface UserInitializationService {
    UserInfoResponse getUserInitializationData(User user);

}
