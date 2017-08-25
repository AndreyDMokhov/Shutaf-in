package com.shutafin.repository.matching.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.matching.UserMatchingScoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMatchingScoreRepositoryImpl extends AbstractEntityDao<UserMatchingScore> implements UserMatchingScoreRepository {

    @Override
    public UserMatchingScore getUserMatchingScore(User userOrigin, User userToMatch) {
        return (UserMatchingScore) getSession()
                .createQuery("from UserMatchingScore ums where ums.userOrigin.id = :userOriginId and " +
                        "ums.userToMatch.id = :userToMatchId")
                .setParameter("userOriginId", userOrigin.getId())
                .setParameter("userToMatchId", userToMatch.getId())
                .uniqueResult();
    }

    @Override
    public List<UserMatchingScore> getAllUserMatchingScores(User userOrigin) {
        return getSession()
                .createQuery("from UserMatchingScore ums where ums.userOrigin.id = :userOriginId")
                .setParameter("userOriginId", userOrigin.getId())
                .list();
    }
}
