package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class UserLoginLogRepositoryImpl extends AbstractEntityDao<UserLoginLog> implements UserLoginLogRepository {

    private static final int MINUTES_FOR_TRIES = 10;

    @Override
    public Long countLoginTries(UserLoginLog userLoginLog) {
        Timestamp timeStampNow = new Timestamp(System.currentTimeMillis());
        Timestamp timeStampDelay = new Timestamp(timeStampNow.getTime() - MINUTES_FOR_TRIES*60*1000);
       return (Long) getSession()
               .createQuery("SELECT count(*) FROM UserLoginLog where user = :user and" +
                        " createdDate between :timeFrom and :timeTo ")
               .setParameter("timeTo",timeStampNow)
               .setParameter("timeFrom", timeStampDelay)
               .setParameter("user", userLoginLog.getUser())
               .uniqueResult();
    }
}
