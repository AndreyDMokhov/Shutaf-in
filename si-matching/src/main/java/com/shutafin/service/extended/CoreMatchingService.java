package com.shutafin.service.extended;


import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;

import java.util.List;
import java.util.Map;

public interface CoreMatchingService {

    UserMatchingScore evaluateUserMatchingScore(Long userOriginId,
                                                Long userToMatchId,
                                                Double maxPossibleScoreOrigin,
                                                Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userOriginAnswers,
                                                Double maxPossibleScoreToMatch,
                                                Map<QuestionExtended, List<UserQuestionExtendedAnswer>> userToMatchAnswers);
    void evaluateAllUserMatchingScores();
}
