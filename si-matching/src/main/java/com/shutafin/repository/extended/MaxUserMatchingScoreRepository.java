package com.shutafin.repository.extended;


import com.shutafin.model.entities.extended.MaxUserMatchingScore;
import com.shutafin.repository.base.BaseJpaRepository;


public interface MaxUserMatchingScoreRepository extends BaseJpaRepository<MaxUserMatchingScore, Long> {

    MaxUserMatchingScore findByUserId(Long userId);
}
