package com.shutafin.repository.extended;


import com.shutafin.model.entities.extended.MaxUserMatchingScore;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;


public interface MaxUserMatchingScoreRepository extends BaseJpaRepository<MaxUserMatchingScore, Long> {

    MaxUserMatchingScore findByUserId(Long userId);
    List<MaxUserMatchingScore> findByUserIdIn(List<Long> usersIds);
}
