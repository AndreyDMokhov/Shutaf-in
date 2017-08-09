package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.repository.base.PersistentDao;

public interface UserInfoRepository extends PersistentDao<UserInfo> {

    UserInfo findUserInfo(User user);

}
