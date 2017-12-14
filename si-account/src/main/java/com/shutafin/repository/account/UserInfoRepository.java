package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserInfoRepository extends BaseJpaRepository<UserInfo, Long> {

    UserInfo findByUser(User user);

    @Query("SELECT ui.user.id FROM UserInfo ui WHERE ui.user.id in (:usersIdList) " +
            "AND ui.dateOfBirth between :fromBirthDate and :toBirthDate")
    List<Long> filterUsersFromListByAge(@Param("usersIdList") List<Long> usersIdList,
                                        @Param("fromBirthDate") Date fromBirthDate,
                                        @Param("toBirthDate") Date toBirthDate);
}
