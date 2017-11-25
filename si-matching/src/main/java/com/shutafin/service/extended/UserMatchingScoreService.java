package com.shutafin.service.extended;



import com.shutafin.model.entities.extended.UserMatchingScore;

import java.util.Map;

public interface UserMatchingScoreService {

    UserMatchingScore getMatchingScore(Long userOriginId, Long userToMatchId);
    Map<Long, Integer> getUserMatchingScores(Long userOriginId);
    Long deleteUserMatchingScores(Long userId);

}
