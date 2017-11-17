package com.shutafin.repository.account;

import com.shutafin.model.entities.User;


public interface UserSessionRepositoryCustom {

    User findUserBySessionIdAndIsValid(String sessionId, boolean isValid);
    int updateAllValidExpiredUserSessions();

}
