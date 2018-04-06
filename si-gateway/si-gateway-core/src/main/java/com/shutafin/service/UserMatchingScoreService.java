package com.shutafin.service;

import com.shutafin.model.web.matching.MatchedUsersScoresSearchResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;

public interface UserMatchingScoreService {

    UserMatchingScoreDTO getMatchingScore(Long userOrigin, Long userToMatch);
    MatchedUsersScoresSearchResponse getUserMatchingScores(Long userOrigin);
    Long deleteUserMatchingScores(Long userId);

}
