package com.shutafin.service.extended.impl;


import com.shutafin.model.entities.extended.MaxUserMatchingScore;
import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;
import com.shutafin.repository.extended.MaxUserMatchingScoreRepository;
import com.shutafin.repository.extended.UserMatchingScoreRepository;
import com.shutafin.sender.account.UserFilterControllerSender;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.extended.AsyncSaveMatchingScoreService;
import com.shutafin.service.extended.CoreMatchingService;
import com.shutafin.service.extended.UserMatchingScoreService;
import com.shutafin.service.extended.UserQuestionExtendedAnswerService;
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

    @Autowired
    private MaxUserMatchingScoreRepository maxUserMatchingScoreRepository;

    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @Autowired
    private AsyncSaveMatchingScoreService asyncSaveMatchingScoreService;

    @Override
    public UserMatchingScore getMatchingScore(Long userOriginId,
                                              Long userToMatchId,
                                              Double maxPossibleScoreOrigin,
                                              Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers,
                                              Double maxPossibleScoreToMatch,
                                              Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers) {
        UserMatchingScore score = userMatchingScoreRepository.findByUserOriginIdAndUserToMatchId(userOriginId, userToMatchId);
        if (score == null) {
            score = coreMatchingService.evaluateUserMatchingScore(userOriginId, userToMatchId, maxPossibleScoreOrigin, userOriginAnswers, maxPossibleScoreToMatch, userToMatchAnswers);
        }
        return score;
    }

    @Override
    public Map<Long, Integer> getUserMatchingScores(Long userOriginId) {
        List<Long> usersToMatch = userMatchService.findMatchingUsers(userOriginId);
        Map<Long, Integer> userMatchingScoresValues = new HashMap<>();
        Map<Long, UserMatchingScore> userMatchingScores = new HashMap<>();

        Double maxPossibleScoreOrigin = maxUserMatchingScoreRepository.findByUserId(userOriginId).getScore().doubleValue();
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers = userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userOriginId);

        Map<Long, Double> maxUserMatchingScoresMap = createMaxUserMatchingScoresMap(maxUserMatchingScoreRepository.findByUserIdIn(usersToMatch));
        Map<Long, Map<QuestionExtended, List<UserQuestionExtendedAnswer>>> usersQuestionExtendedAnswers = userQuestionExtendedAnswerService.getUserQuestionExtendedAnswersByUserIds(usersToMatch);
        for (Long userToMatch : usersToMatch) {
            Double maxPossibleScoreToMatch = maxUserMatchingScoresMap.get(userToMatch);
            Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers = usersQuestionExtendedAnswers.get(userToMatch);

            UserMatchingScore score = getMatchingScore(userOriginId, userToMatch, maxPossibleScoreOrigin, userOriginAnswers, maxPossibleScoreToMatch, userToMatchAnswers);
            userMatchingScoresValues.put(userToMatch, score.getScore());
            userMatchingScores.put(userToMatch, score);
        }
        asyncSaveMatchingScoreService.saveMatchingScores(userMatchingScores.values());
        return userMatchingScoresValues;
    }

    private Map<Long,Double> createMaxUserMatchingScoresMap(List<MaxUserMatchingScore> maxUserMatchingScores) {
        Map<Long,Double> res = new HashMap<>();
        for(MaxUserMatchingScore maxUserMatchingScore : maxUserMatchingScores){
            res.put(maxUserMatchingScore.getUserId(), maxUserMatchingScore.getScore().doubleValue());
        }

        return res;
    }

    @Override
    public List<UserSearchResponse> getMatchedUserSearchResponses(Long userId, AccountUserFilterRequest accountUserFilterRequest){
        Map<Long, Integer> userMatchingScores = getUserMatchingScores(userId);
        if(userMatchingScores.isEmpty()){
            return new ArrayList<>();
        }
        List<Long> usersList = new ArrayList<>(userMatchingScores.keySet());
        accountUserFilterRequest.setUserIds(usersList);
        List<UserSearchResponse> userSearchResponses = userFilterControllerSender.saveUserFiltersAndGetUsers(userId,accountUserFilterRequest);
        return userSearchResponses.stream().peek(r -> r.setScore(userMatchingScores.get(r.getUserId()))).collect(Collectors.toList());
    }

    @Override
    public Long deleteUserMatchingScores(Long userId) {
        return userMatchingScoreRepository.deleteAllByUserOriginIdOrUserToMatchId(userId, userId);
    }

    @Override
    public UserMatchingScoreDTO getOneMatchingScore(Long userOriginId, Long userToMatch) {
        Double maxPossibleScoreOrigin = maxUserMatchingScoreRepository.findByUserId(userOriginId).getScore().doubleValue();
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers = userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userOriginId);
        Double maxPossibleScoreToMatch = maxUserMatchingScoreRepository.findByUserId(userToMatch).getScore().doubleValue();
        Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers = userQuestionExtendedAnswerService.getAllUserQuestionExtendedAnswers(userToMatch);

        UserMatchingScore userMatchingScore = getMatchingScore(userOriginId, userToMatch, maxPossibleScoreOrigin, userOriginAnswers, maxPossibleScoreToMatch, userToMatchAnswers);

        return new UserMatchingScoreDTO(userMatchingScore.getUserOriginId(),
                userMatchingScore.getUserToMatchId(),
                userMatchingScore.getScore(),
                userMatchingScore.getCreatedDate(),
                userMatchingScore.getUpdatedDate());

    }
}
