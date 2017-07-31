package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.repository.base.PersistentDao;

public interface UserImageRepository extends PersistentDao<UserImage> {
    UserImage findUserImage(User user);
}
