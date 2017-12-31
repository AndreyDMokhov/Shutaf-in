package com.shutafin.service;

import com.shutafin.model.web.matching.UserMatchingScoreDTO;

import java.util.Map;

public interface UserMatchingScoreService {

    UserMatchingScoreDTO getMatchingScore(Long userOrigin, Long userToMatch);
    Map<Long, Integer> getUserMatchingScores(Long userOrigin);
    Long deleteUserMatchingScores(Long userId);

}
