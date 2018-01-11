package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.types.CompressionType;
import com.shutafin.model.web.account.AccountUserImageWeb;

import java.util.List;

public interface UserImageService {

    UserImage addUserImage(AccountUserImageWeb image, User user, CompressionType compressionType);
    UserImage getUserImage(User user, Long userImageId);

    UserImage getCompressedUserImage(User user);
    void deleteUserImage(User user, Long userImageId);
    void createUserImageDirectory(User user);
    List<UserImage> getAllUserImages(User user);
    UserImage getOriginalUserImage(UserImage compressedUserImage);
    UserImage getOriginalUserImage(User user);
    UserImage getCompressedUserImage(UserImage originalUserImage);
    String getDefaultImageBase64();
}
