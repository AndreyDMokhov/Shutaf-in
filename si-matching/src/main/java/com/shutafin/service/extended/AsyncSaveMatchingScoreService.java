package com.shutafin.service.extended;

import com.shutafin.model.entities.extended.UserMatchingScore;

import java.util.Collection;

/**
 * Created by evgeny on 2/14/2018.
 */
public interface AsyncSaveMatchingScoreService {
    void saveMatchingScores(Collection<UserMatchingScore> matchingScores);
}
