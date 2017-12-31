package com.shutafin.repository.account;

import com.shutafin.model.entities.UserSession;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.Date;


public interface UserSessionRepository extends BaseJpaRepository<UserSession, Long>, UserSessionRepositoryCustom {
    UserSession findBySessionId(String sessionId);
    UserSession findBySessionIdAndIsValidTrue(String sessionId);
    void deleteAllByIsValidFalseAndExpirationDateAfter(Date date);
}
