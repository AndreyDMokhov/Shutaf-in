package com.shutafin.service.extended.impl;


import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;
import com.shutafin.repository.extended.UserMatchingScoreRepository;
import com.shutafin.sender.account.UserFilterControllerSender;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.extended.CoreMatchingService;
import com.shutafin.service.extended.UserMatchingScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserMatchingScoreServiceImpl implements UserMatchingScoreService {

    @Autowired
    private CoreMatchingService coreMatchingService;

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Autowired
    public UserFilterControllerSender userFilterControllerSender;

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
    public List<UserSearchResponse> getMatchedUserSearchResponses(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        Map<Long, Integer> userMatchingScores = getUserMatchingScores(userId);
        if (userMatchingScores.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> usersList = new ArrayList<>(userMatchingScores.keySet());
        accountUserFilterRequest.setUserIds(usersList);
        List<UserSearchResponse> userSearchResponses = userFilterControllerSender.saveUserFiltersAndGetUsers(userId, accountUserFilterRequest);


        return userSearchResponses
                .stream()
                .peek(r -> r.setScore(userMatchingScores.get(r.getUserId())))
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteUserMatchingScores(Long userId) {
        return userMatchingScoreRepository.deleteAllByUserOriginIdOrUserToMatchId(userId, userId);
    }

    @Override
    public UserMatchingScoreDTO getOneMatchingScore(Long userOriginId, Long userToMatch) {
        UserMatchingScore userMatchingScore = getMatchingScore(userOriginId, userToMatch);
        return new UserMatchingScoreDTO(userMatchingScore.getUserOriginId(),
                userMatchingScore.getUserToMatchId(),
                userMatchingScore.getScore(),
                userMatchingScore.getCreatedDate(),
                userMatchingScore.getUpdatedDate());

    }
}
