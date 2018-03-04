package com.shutafin.repository.extended;



import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserQuestionExtendedAnswerRepository extends BaseJpaRepository<UserQuestionExtendedAnswer, Long> {
    List<UserQuestionExtendedAnswer> findAllByUserIdAndQuestion(Long userId, QuestionExtended question);
    List<UserQuestionExtendedAnswer> findAllByUserId(Long userId);
    List<UserQuestionExtendedAnswer> findAllByUserIdIn(List<Long> userIds);

    @Query(" SELECT uqea.userId " +
            " FROM UserQuestionExtendedAnswer uqea " +
            " WHERE uqea.userId IN (:usersToMatch) " +
            " GROUP BY uqea.userId " +
            " ORDER BY SUM(uqea.importance.weight) DESC "
    )
    List<Long> getUsersToMatchSortedByUserAnswersWeightSum(@Param("usersToMatch") List<Long> usersToMatch);
}
