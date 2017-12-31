package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.account.UserLoginLogRepositoryCustom;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class UserLoginLogRepositoryImpl implements UserLoginLogRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean hasExceededMaxLoginTries(User user, int amountOfAllowedMaxTries, int maxTimeForMinutes) {
        Date timeNow = DateTime.now().toDate();
        Date timeDelay = DateTime.now().minusMinutes(maxTimeForMinutes).toDate();
        Long countTries = (Long) entityManager
                .createQuery("SELECT COUNT(isLoginSuccess) FROM UserLoginLog WHERE user = :user " +
                        "AND id > COALESCE((SELECT MAX(id) FROM UserLoginLog  WHERE user = :user AND isLoginSuccess = TRUE),0) " +
                        "AND createdDate BETWEEN :timeFrom AND :timeTo ")
                .setParameter("timeTo", timeNow)
                .setParameter("timeFrom", timeDelay)
                .setParameter("user", user)
                .getSingleResult();
        return countTries >= amountOfAllowedMaxTries;
    }
}
