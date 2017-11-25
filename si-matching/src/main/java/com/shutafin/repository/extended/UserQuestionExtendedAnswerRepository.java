package com.shutafin.repository.extended;



import com.shutafin.model.entities.extended.QuestionExtended;
import com.shutafin.model.entities.extended.UserQuestionExtendedAnswer;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;

public interface UserQuestionExtendedAnswerRepository extends BaseJpaRepository<UserQuestionExtendedAnswer, Long> {
    List<UserQuestionExtendedAnswer> findAllByUserIdAndQuestion(Long userId, QuestionExtended question);
    List<UserQuestionExtendedAnswer> findAllByUserId(Long userId);
}
