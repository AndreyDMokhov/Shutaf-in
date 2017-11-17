package com.shutafin.repository.account;

import com.shutafin.model.entities.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {

    UserSession findBySessionId(String sessionId);
    UserSession findBySessionIdAndIsValid(String sessionId, boolean isValid);
    Stream<UserSession> findAllByExpirationDateBefore(Date date);
    void deleteAllByExpirationDateBeforeAndIsValid(Date date, boolean isValid);

}
