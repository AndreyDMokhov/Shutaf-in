package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.UserImageRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserImageRepositoryImpl extends AbstractEntityDao<UserImage> implements UserImageRepository {

    @Override
    public UserImage findUserImage(User user) {
        return (UserImage) getSession()
                .createQuery("from UserImage ui where ui.user.id = :userId")
                .setParameter("userId", user.getId()).uniqueResult();
    }

    @Override
    public List<UserImage> findAllUserImages(User user) {
        return getSession()
                .createQuery("from UserImage ui where ui.user.id = :userId")
                .setParameter("userId", user.getId()).list();
    }
}
