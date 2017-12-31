package com.shutafin.repository.account;

public interface UserSessionRepositoryCustom {

    Long findUserIdBySessionIdAndIsValid(String sessionId, boolean isValid);

    int updateAllValidExpiredUserSessions();

}
