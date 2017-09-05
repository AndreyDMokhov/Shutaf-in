package com.shutafin.repository.matching;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.MaxUserMatchingScore;
import com.shutafin.repository.base.PersistentDao;

public interface MaxUserMatchingScoreRepository extends PersistentDao<MaxUserMatchingScore> {

    MaxUserMatchingScore getUserMaxMatchingScore(User user);
}
