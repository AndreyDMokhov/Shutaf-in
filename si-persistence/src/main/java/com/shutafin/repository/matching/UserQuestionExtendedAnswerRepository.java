package com.shutafin.repository.matching;


import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

public interface UserQuestionExtendedAnswerRepository extends PersistentDao<UserQuestionExtendedAnswer> {
    List<UserQuestionExtendedAnswer> getUserQuestionExtendedAnswer(User user, QuestionExtended question);
    List<UserQuestionExtendedAnswer> getAllUserQuestionExtendedAnswers(User user);
}
