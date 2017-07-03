package com.shutafin.service;


import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInit;

public interface UserInitializationService {
    UserInit getUserInitData(User user);

}
