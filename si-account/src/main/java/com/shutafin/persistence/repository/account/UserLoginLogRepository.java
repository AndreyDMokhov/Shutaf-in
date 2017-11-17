package com.shutafin.persistence.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserLoginLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

@Repository
public interface UserLoginLogRepository extends CrudRepository<UserLoginLog, Long> {
    Stream<UserLoginLog> findAllByUserAndCreatedDateBetween(User user, Date timeFrom, Date timeTo);
    UserLoginLog findTopByIsLoginSuccessOrderByIdDesc(boolean isLoginSuccess);
}
