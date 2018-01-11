package com.shutafin.service;

import com.shutafin.model.web.account.AccountUserImageWeb;

public interface UserImageService {

    AccountUserImageWeb addUserImage(AccountUserImageWeb image, Long userId);

    AccountUserImageWeb getCompressedUserImage(Long userId);
    AccountUserImageWeb getOriginalUserImage(Long userId);

    void deleteUserImage(Long userId, Long userImageId);
}
