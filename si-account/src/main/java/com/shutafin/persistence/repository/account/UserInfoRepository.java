package com.shutafin.persistence.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    UserInfo findByUser(User user);

}
