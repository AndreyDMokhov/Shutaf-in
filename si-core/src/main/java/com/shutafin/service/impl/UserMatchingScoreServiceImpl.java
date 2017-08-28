package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.repository.matching.UserMatchingScoreRepository;
import com.shutafin.service.CoreMatchingService;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserMatchingScoreService;
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
    public UserMatchingScore getMatchingScore(User userOrigin, User userToMatch) {
        UserMatchingScore score = userMatchingScoreRepository.getUserMatchingScore(userOrigin, userToMatch);
        if (score == null) {
            score = coreMatchingService.evaluateUserMatchingScore(userOrigin, userToMatch);
        }
        return score;
    }

    @Override
    public Map<Long, Integer> getUserMatchingScores(User userOrigin) {
        List<User> usersToMatch = userMatchService.findPartners(userOrigin);
        Map<Long, Integer> userMatchingScores = new HashMap<>();
        for (User userToMatch : usersToMatch) {
            UserMatchingScore score = userMatchingScoreRepository.getUserMatchingScore(userOrigin, userToMatch);
            if (score == null) {
                score = coreMatchingService.evaluateUserMatchingScore(userOrigin, userToMatch);
            }
            userMatchingScores.put(userToMatch.getId(), score.getScore());
        }
        return userMatchingScores;
    }
}
