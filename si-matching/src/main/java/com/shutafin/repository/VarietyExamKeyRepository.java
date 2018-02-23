package com.shutafin.repository;

import com.shutafin.model.match.VarietyExamKey;
import com.shutafin.repository.base.BaseJpaRepository;

public interface VarietyExamKeyRepository extends BaseJpaRepository<VarietyExamKey, Long> {
    VarietyExamKey getFirstByUserExamKey(String key);
}
