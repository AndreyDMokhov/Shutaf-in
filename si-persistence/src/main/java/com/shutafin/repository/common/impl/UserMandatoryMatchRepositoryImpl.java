package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserMandatoryMatchResult;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserMandatoryMatchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 8/10/2017.
 */
@Repository
public class UserMandatoryMatchRepositoryImpl extends AbstractEntityDao<UserMandatoryMatchResult> implements UserMandatoryMatchRepository {
    @Override
    public List<User> findPartners(UserMandatoryMatchResult userMandatoryMatchResult) {

        return (List<User>) getSession()
                .createQuery("SELECT s FROM User s where s.userMatchExpression REGEXP :matchRegExp")
                .setParameter("matchRegExp", userMandatoryMatchResult.getMatchRegExp())
                .getResultList();
    }
}
