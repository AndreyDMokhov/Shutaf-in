package com.shutafin.repository;


import com.shutafin.model.match.UserExamKey;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserExamKeyRepository extends BaseJpaRepository<UserExamKey, Long> {
    void deleteByUserId(Long userId);
    UserExamKey findByUserId(Long userId);

    @Query("SELECT uek.userId FROM UserExamKey uek WHERE uek.examKeyRegExp in (:keys) AND uek.isMatchingEnabled = 1")
    List<Long> getMatchedUsers(@Param("keys") List<String> keys);

    @Query("SELECT uek.userId FROM UserExamKey uek WHERE uek.isMatchingEnabled = 1")
    List<Long> findAllUserIds();
}
