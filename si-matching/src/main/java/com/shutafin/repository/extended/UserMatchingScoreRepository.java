package com.shutafin.repository.extended;


import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;


public interface UserMatchingScoreRepository extends BaseJpaRepository<UserMatchingScore, Long> {

    UserMatchingScore findByUserOriginIdAndUserToMatchId(Long userOriginId, Long userToMatchId);
    List<UserMatchingScore> findByUserOriginId(Long userOriginId);
    Long deleteAllByUserOriginIdOrUserToMatchId(Long userOriginId, Long userToMatchId);

}
