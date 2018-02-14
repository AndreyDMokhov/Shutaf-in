package com.shutafin.service.extended;


import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;

import java.util.List;
import java.util.Map;

public interface UserMatchingScoreService {

    UserMatchingScore getMatchingScore(Long userOriginId, Long userToMatchId, Double maxPossibleScoreOrigin, Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers, Double maxPossibleScoreToMatch, Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers);
    Map<Long,Integer> getUserMatchingScores(Long userOriginId);
    List<UserSearchResponse> getMatchedUserSearchResponses(Long userId, AccountUserFilterRequest accountUserFilterRequest);
    Long deleteUserMatchingScores(Long userId);
    UserMatchingScoreDTO getOneMatchingScore(Long userOriginId, Long userToMatch);
}
