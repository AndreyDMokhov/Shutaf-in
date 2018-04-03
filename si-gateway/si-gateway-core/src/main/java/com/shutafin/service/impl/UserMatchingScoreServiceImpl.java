package com.shutafin.service.impl;

import com.shutafin.model.web.matching.MatchedUsersScoresSearchResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;
import com.shutafin.sender.matching.UserMatchingScoreControllerSender;
import com.shutafin.service.UserMatchingScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class UserMatchingScoreServiceImpl implements UserMatchingScoreService {

    private UserMatchingScoreControllerSender userMatchingScoreControllerSender;

    @Autowired
    public UserMatchingScoreServiceImpl(UserMatchingScoreControllerSender userMatchingScoreControllerSender) {
        this.userMatchingScoreControllerSender = userMatchingScoreControllerSender;
    }

    @Override
    public UserMatchingScoreDTO getMatchingScore(Long userOrigin, Long userToMatch) {
        return userMatchingScoreControllerSender.getOneMatchingScore(userOrigin, userToMatch);
    }

    @Override
    public MatchedUsersScoresSearchResponse getUserMatchingScores(Long userOrigin) {
        if (userOrigin == null) {
            return MatchedUsersScoresSearchResponse.builder()
                    .matchedUsersScoresPerPage(new HashMap<>())
                    .totalUsers(0)
                    .build();
        }
        return userMatchingScoreControllerSender.getUserMatchingScores(userOrigin);
    }

    @Override
    public Long deleteUserMatchingScores(Long userId) {
        return userMatchingScoreControllerSender.deleteUserMatchingScores(userId);
    }
}
