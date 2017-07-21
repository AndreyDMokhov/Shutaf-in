package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

public interface UserImageRepository extends PersistentDao<UserImage> {
    UserImage findUserImage(User user);
    List<UserImage> findAllUserImages(User user);
}
