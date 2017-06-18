package com.shutafin.repository.impl;

import com.shutafin.model.common.UserInfo;
import com.shutafin.repository.UserInfoRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class UserInfoRepositoryImpl extends AbstractEntityDao<UserInfo> implements UserInfoRepository {


    @Override
    public UserInfo getByUserId(Long userId) {
        try {
            return  (UserInfo) getSession()
                    .createQuery("from UserInfo ui where ui.user.id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }

    }
}
