package com.shutafin.repository.matching.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.MaxUserMatchingScore;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.matching.MaxUserMatchingScoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MaxUserMatchingScoreRepositoryImpl extends AbstractEntityDao<MaxUserMatchingScore> implements MaxUserMatchingScoreRepository {
    @Override
    public MaxUserMatchingScore getUserMaxMatchingScore(User user) {
        return (MaxUserMatchingScore) getSession()
                .createQuery("from MaxUserMatchingScore sc where sc.user.id = :userId")
                .setParameter("userId", user.getId())
                .uniqueResult();
    }
}
