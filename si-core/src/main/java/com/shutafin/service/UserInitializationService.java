package com.shutafin.service;


import com.shutafin.model.web.user.UserInit;

public interface UserInitializationService {
    UserInit getAuthenticatedUser(String sessionId);

}
