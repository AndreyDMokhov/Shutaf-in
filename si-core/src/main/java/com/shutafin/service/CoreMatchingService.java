package com.shutafin.service;


import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.UserMatchingScore;

public interface CoreMatchingService {

    UserMatchingScore evaluateUserMatchingScore(User userOrigin, User userToMatch);
    void evaluateAllUserMatchingScores();
}
