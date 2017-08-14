package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserMandatoryMatchResult;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserMandatoryMatchRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeny on 8/10/2017.
 */
@Repository
public class UserMandatoryMatchRepositoryImpl extends AbstractEntityDao<UserMandatoryMatchResult> implements UserMandatoryMatchRepository {
    @Override
    public List<User> findPartners(User user) {

        String userMatchRegExp =  (String) getSession()
                .createQuery("SELECT ummr.matchRegExp FROM UserMandatoryMatchResult ummr where ummr.user = :user")
                .setParameter("user", user)
                .getSingleResult();

        if (userMatchRegExp == null || userMatchRegExp.isEmpty()){
            return new ArrayList<User>();
        }

        return (List<User>) getSession()
                .createQuery("SELECT ummr.user FROM UserMandatoryMatchResult ummr where ummr.matchRegExp = ':matchRegExp' and ummr.user <> :user")
                .setParameter("matchRegExp", userMatchRegExp)
                .setParameter("user", user)
                .getResultList();
    }
}
