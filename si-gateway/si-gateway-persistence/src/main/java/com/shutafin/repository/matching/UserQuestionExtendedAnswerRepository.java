package com.shutafin.repository.matching;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;
@Deprecated
public interface UserQuestionExtendedAnswerRepository extends BaseJpaRepository<UserQuestionExtendedAnswer, Long> {
    List<UserQuestionExtendedAnswer> findAllByUserAndQuestion(User user, QuestionExtended question);
    List<UserQuestionExtendedAnswer> findAllByUser(User user);
}
