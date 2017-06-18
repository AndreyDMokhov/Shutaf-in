package com.shutafin.repository;

import com.shutafin.model.common.UserInfo;
import com.shutafin.repository.base.PersistentDao;


public interface UserInfoRepository extends PersistentDao<UserInfo> {

    UserInfo getByUserId(Long userId);
}
