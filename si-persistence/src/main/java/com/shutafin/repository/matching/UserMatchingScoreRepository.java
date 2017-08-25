package com.shutafin.repository.matching;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

public interface UserMatchingScoreRepository extends PersistentDao<UserMatchingScore> {

    UserMatchingScore getUserMatchingScore(User userOrigin, User userToMatch);
    List<UserMatchingScore> getAllUserMatchingScores(User userOrigin);

}
