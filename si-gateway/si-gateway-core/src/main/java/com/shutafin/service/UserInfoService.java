package com.shutafin.service;

import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;


public interface UserInfoService {

    AccountUserInfoResponseDTO getUserInfo(Long userId);
    void updateUserInfo(AccountUserInfoRequest userInfoRequest, Long userId);

}
