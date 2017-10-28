package com.shutafin.repository.common;

import com.shutafin.model.entities.match.VarietyExamKey;
import com.shutafin.repository.base.BaseJpaRepository;


/**
 * Created by evgeny on 9/9/2017.
 */
public interface VarietyExamKeyRepository extends BaseJpaRepository<VarietyExamKey, Long>, VarietyExamKeyRepositoryCustom {
    VarietyExamKey findByUserExamKey(String userExamKey);
}
