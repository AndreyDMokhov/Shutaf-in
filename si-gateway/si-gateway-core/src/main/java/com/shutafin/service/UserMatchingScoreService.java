package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.UserMatchingScore;

import java.util.Map;
@Deprecated
public interface UserMatchingScoreService {

    UserMatchingScore getMatchingScore(User userOrigin, User userToMatch);
    Map<Long, Integer> getUserMatchingScores(User userOrigin);
    Long deleteUserMatchingScores(User user);

}
