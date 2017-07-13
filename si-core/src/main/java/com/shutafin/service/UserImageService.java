package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;

public interface UserImageService {

    void addUserImage(String image, User user);
    UserImage getUserImage(User user, Long userImageId);
    void deleteUserImage(UserImage userImage);
    void createUserImageDirectory(User user);

}
