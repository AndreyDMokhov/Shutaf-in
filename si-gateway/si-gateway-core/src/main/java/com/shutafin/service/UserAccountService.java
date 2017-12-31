package com.shutafin.service;

import com.shutafin.model.web.account.AccountUserImageWeb;


public interface UserAccountService {

    AccountUserImageWeb updateProfileImage(Long userId, AccountUserImageWeb userImageWeb);
    AccountUserImageWeb findUserAccountProfileImage(Long userId);
    void deleteUserAccountProfileImage(Long userId);

}
