package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseJpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.id in (:usersIdList) AND " +
            "concat(u.firstName, ' ', u.lastName) = :fullName " +
            "OR u.id in (:usersIdList) AND concat(u.lastName, ' ', u.firstName) = :fullName ")
    List<Long> findAllByFullName(@Param("usersIdList") List<Long> usersIdList,
                                 @Param("fullName") String fullName);
}
