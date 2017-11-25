package com.shutafin.repository.matching;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.MaxUserMatchingScore;
import com.shutafin.repository.base.BaseJpaRepository;

@Deprecated
public interface MaxUserMatchingScoreRepository extends BaseJpaRepository<MaxUserMatchingScore, Long> {

    MaxUserMatchingScore findByUser(User user);
}
