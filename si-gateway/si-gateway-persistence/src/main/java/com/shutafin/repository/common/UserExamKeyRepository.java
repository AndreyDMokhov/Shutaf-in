package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 9/5/2017.
 */
@Deprecated
public interface UserExamKeyRepository extends BaseJpaRepository<UserExamKey, Long> {
    void deleteByUser(User user);
    UserExamKey findByUser(User user);

    @Query("SELECT uek.user FROM UserExamKey uek WHERE uek.examKeyRegExp in (:keys)")
    List<User> getMatchedUsers(@Param("keys") List<String> keys);
}
