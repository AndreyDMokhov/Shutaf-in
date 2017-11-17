package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.repository.base.BaseJpaRepository;

public interface UserInfoRepository extends BaseJpaRepository<UserInfo, Long> {

    UserInfo findByUser(User user);

}
