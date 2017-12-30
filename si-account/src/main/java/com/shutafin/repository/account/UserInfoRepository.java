package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserInfoRepository extends BaseJpaRepository<UserInfo, Long> {

    UserInfo findByUser(User user);

    @Query(" SELECT ui.dateOfBirth FROM UserInfo ui WHERE ui.user.id = :userId")
    Date getBirthDateByUserId(@Param("userId") Long userId);

    @Query(" SELECT ui.gender.id FROM UserInfo ui WHERE ui.user.id = :userId")
    Integer getGenderByUserId(@Param("userId") Long userId);

    @Query("SELECT ui.user.id FROM UserInfo ui WHERE ui.user.id in (:usersIdList) " +
            "AND ui.dateOfBirth between :fromBirthDate and :toBirthDate")
    List<Long> filterUsersFromListByAge(@Param("usersIdList") List<Long> usersIdList,
                                        @Param("fromBirthDate") Date fromBirthDate,
                                        @Param("toBirthDate") Date toBirthDate);

    @Query("SELECT ui.user.id FROM UserInfo ui WHERE ui.user.id in (:usersIdList) " +
            "AND ui.gender.id = :gender")
    List<Long> filterUsersFromListByGender(@Param("usersIdList") List<Long> usersIdList,
                                        @Param("gender") Integer gender);

    @Query("SELECT new com.shutafin.model.web.common.UserSearchResponse " +
            "( u.id, u.firstName, u.lastName, " +
            " ist.imageEncoded, uim.id, " +
            " uin.gender.id, uin.currentCity.id, city.country.id, uin.dateOfBirth) " +
            " FROM UserInfo uin left join uin.currentCity city, User u, " +
            " UserAccount uic left join uic.userImage uim left join uim.imageStorage ist" +
            " WHERE uin.user.id=u.id and uin.user.id=uic.user.id and uin.user.id in (:usersIdList)")
    List<UserSearchResponse> getUserSearchListByUserId(@Param("usersIdList") List<Long> usersIdList);

    @Query("SELECT new com.shutafin.model.web.common.UserSearchResponse " +
            "( u.id, u.firstName, u.lastName, " +
            " ist.imageEncoded, uim.id, " +
            " uin.gender.id, uin.currentCity.id, city.country.id, uin.dateOfBirth) " +
            " FROM UserInfo uin left join uin.currentCity city, User u, " +
            " UserAccount uic left join uic.userImage uim left join uim.imageStorage ist" +
            " WHERE uin.user.id = :userId")
    UserSearchResponse getUserSearchResponseByUserId(@Param("userId") Long usersId);


}
