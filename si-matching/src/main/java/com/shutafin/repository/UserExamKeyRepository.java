package com.shutafin.repository;


import com.shutafin.model.match.UserExamKey;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExamKeyRepository extends BaseJpaRepository<UserExamKey, Long> {
    void deleteByUserId(Long userId);
    UserExamKey findByUserId(Long userId);
    List<UserExamKey> findAllByExamKeyIn(List<String> keys);
}
