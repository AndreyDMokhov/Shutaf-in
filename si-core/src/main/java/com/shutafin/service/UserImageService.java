package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;

public interface UserImageService {

    void addUserImage(UserImageWeb image, User user);
    UserImage getUserImage(User user, Long userImageId);
    void deleteUserImage(User user, Long userImageId);
    void createUserImageDirectory(User user);

}
