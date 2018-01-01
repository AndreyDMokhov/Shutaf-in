package com.shutafin.service;

import com.shutafin.model.web.account.AccountUserImageWeb;

public interface UserImageService {

    AccountUserImageWeb addUserImage(AccountUserImageWeb image, Long userId);

    AccountUserImageWeb getUserImage(Long userId, Long userImageId);

    AccountUserImageWeb getUserImage(Long userId);

    AccountUserImageWeb getOriginalUserImage(Long userId);

    void deleteUserImage(Long userId, Long userImageId);

    //    List<UserImage> getAllUserImages(Long userId);
    String getDefaultImageBase64();

}
