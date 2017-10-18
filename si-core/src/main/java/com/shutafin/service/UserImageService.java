package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.CompressionType;
import com.shutafin.model.entities.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;

import java.util.List;

public interface UserImageService {

    UserImage addUserImage(UserImageWeb image, User user, PermissionType permissionType, CompressionType compressionType);
    UserImage getUserImage(User user, Long userImageId);
    void deleteUserImage(User user, Long userImageId);
    void createUserImageDirectory(User user);
    List<UserImage> getAllUserImages(User user);
    UserImage getOriginalUserImage(UserImage compressedUserImage);
    UserImage getCompressedUserImage(UserImage originalUserImage);

}
