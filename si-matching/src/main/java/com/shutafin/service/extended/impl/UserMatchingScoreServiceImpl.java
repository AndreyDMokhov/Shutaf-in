package com.shutafin.service.extended.impl;


import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.repository.extended.UserMatchingScoreRepository;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.extended.CoreMatchingService;
import com.shutafin.service.extended.UserMatchingScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserMatchingScoreServiceImpl implements UserMatchingScoreService {

    @Autowired
    private CoreMatchingService coreMatchingService;

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Override
    public UserMatchingScore getMatchingScore(Long userOriginId, Long userToMatchId) {
        UserMatchingScore score = userMatchingScoreRepository.findByUserOriginIdAndUserToMatchId(userOriginId, userToMatchId);
        if (score == null) {
            score = coreMatchingService.evaluateUserMatchingScore(userOriginId, userToMatchId);
        }
        return score;
    }

    @Override
    public Map<Long, Integer> getUserMatchingScores(Long userOriginId) {
        List<Long> usersToMatch = userMatchService.findMatchingUsers(userOriginId);
        Map<Long, Integer> userMatchingScores = new HashMap<>();
        for (Long userToMatch : usersToMatch) {
            UserMatchingScore score = getMatchingScore(userOriginId, userToMatch);
            userMatchingScores.put(userToMatch, score.getScore());
        }
        return userMatchingScores;
    }

    @Override
    public Long deleteUserMatchingScores(Long userId) {
        return userMatchingScoreRepository.deleteAllByUserOriginIdOrUserToMatchId(userId, userId);
    }
}
