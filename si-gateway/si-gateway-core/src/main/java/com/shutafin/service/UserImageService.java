package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.account.AccountUserImageWeb;

import java.util.List;

public interface UserImageService {

    AccountUserImageWeb addUserImage(AccountUserImageWeb image, User user);
    AccountUserImageWeb getUserImage(User user, Long userImageId);
    void deleteUserImage(User user, Long userImageId);
    void createUserImageDirectory(User user);
    List<UserImage> getAllUserImages(User user);
    UserImage getOriginalUserImage(UserImage compressedUserImage);
    String getDefaultImageBase64();

}
