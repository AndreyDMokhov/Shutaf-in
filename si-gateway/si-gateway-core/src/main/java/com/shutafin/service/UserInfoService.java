package com.shutafin.service;

import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.initialization.InitializationResponse;


public interface UserInfoService {

    AccountUserInfoResponseDTO getUserInfo(Long userId);
    InitializationResponse updateUserInfo(AccountUserInfoRequest userInfoRequest, Long userId);

}
