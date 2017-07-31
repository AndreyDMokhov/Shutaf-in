package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserImageRepositoryImpl extends AbstractEntityDao<UserImage> implements UserImageRepository {

    @Override
    public UserImage findUserImage(User user) {
        return (UserImage) getSession()
                .createQuery("from UserImage ui where ui.user.id = :userId")
                .setParameter("userId", user.getId()).uniqueResult();
    }
}
