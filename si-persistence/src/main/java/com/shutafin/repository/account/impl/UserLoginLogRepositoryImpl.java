package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.repository.account.UserLoginLogRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public class UserLoginLogRepositoryImpl extends AbstractEntityDao<UserLoginLog> implements UserLoginLogRepository {

    @Override
    public boolean isLoginFailsMoreThanTries(User user, int nMaxTries, int timeForTriesInMin) {
        Date timeNow = DateTime.now().toDate();
        Date timeDelay = DateTime.now().minusMinutes(timeForTriesInMin).toDate();
        Long countTries = (Long) getSession()
                .createQuery("SELECT COUNT(isLoginSuccess) FROM UserLoginLog WHERE user = :user " +
                        "AND id > COALESCE((SELECT MAX(id) FROM UserLoginLog  WHERE user = :user AND isLoginSuccess = TRUE),0) " +
                        "AND createdDate BETWEEN :timeFrom AND :timeTo ")
                .setParameter("timeTo", timeNow)
                .setParameter("timeFrom", timeDelay)
                .setParameter("user", user)
                .uniqueResult();
        return countTries >= nMaxTries ? true : false;
    }
}

