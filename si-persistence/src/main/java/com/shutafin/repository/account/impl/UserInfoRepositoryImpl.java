package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepositoryImpl extends AbstractEntityDao<UserInfo> implements UserInfoRepository {

    @Override
    public UserInfo findUserInfo(User user) {
        return (UserInfo) getSession()
                .createQuery("from UserInfo ui where ui.user.id = :userId")
                .setParameter("userId", user.getId()).uniqueResult();
    }

}
