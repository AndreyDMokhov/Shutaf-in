package com.shutafin.service.extended;


import com.shutafin.model.entities.extended.UserMatchingScore;

public interface CoreMatchingService {

    UserMatchingScore evaluateUserMatchingScore(Long userOriginId, Long userToMatchId);
    void evaluateAllUserMatchingScores();
}
