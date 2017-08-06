package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;


@Repository
public class UserLoginLogRepositoryImpl extends AbstractEntityDao<UserLoginLog> implements UserLoginLogRepository {

    private static final int MINUTES_FOR_TRIES = 10;

    @Override
    public Long countLoginFails(User user) {
        Timestamp timeStampNow = new Timestamp(System.currentTimeMillis());
        Timestamp timeStampDelay = new Timestamp(timeStampNow.getTime() - MINUTES_FOR_TRIES*60*1000);
       return (Long) getSession()
                       .createQuery("SELECT COUNT(isLoginSuccess) FROM UserLoginLog WHERE user = :user " +
                       "AND id > COALESCE((SELECT MAX(id) FROM UserLoginLog  WHERE user = :user AND isLoginSuccess = TRUE),0) " +
                       "AND createdDate BETWEEN :timeFrom AND :timeTo ")
               .setParameter("timeTo",timeStampNow)
               .setParameter("timeFrom", timeStampDelay)
               .setParameter("user", user)
               .uniqueResult();
    }
}
