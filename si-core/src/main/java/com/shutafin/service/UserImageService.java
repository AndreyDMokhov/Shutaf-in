package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;

import java.util.List;

public interface UserImageService {

    UserImage addUserImage(UserImageWeb image, User user, PermissionType permissionType);
    UserImage getUserImage(User user, Long userImageId);
    void deleteUserImage(User user, Long userImageId);
    void createUserImageDirectory(User user);
    List<UserImage> getAllUserImages(User user);
    UserImage compressUserImage(UserImage userImage);

}
