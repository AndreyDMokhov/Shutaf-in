package com.shutafin.repository.matching.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.matching.UserQuestionExtendedAnswer;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.matching.UserQuestionExtendedAnswerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserQuestionExtendedAnswerRepositoryImpl extends AbstractEntityDao<UserQuestionExtendedAnswer>
        implements UserQuestionExtendedAnswerRepository {

    @Override
    public List<UserQuestionExtendedAnswer> getUserQuestionExtendedAnswer(User user, QuestionExtended question) {
        return getSession()
                .createQuery("SELECT uqea FROM UserQuestionExtendedAnswer uqea where uqea.user.id = :userId " +
                        "AND uqea.question.id = :questionId")
                .setParameter("userId", user.getId())
                .setParameter("questionId", question.getId())
                .list();
    }

    @Override
    public List<UserQuestionExtendedAnswer> getAllUserQuestionExtendedAnswers(User user) {
        return getSession()
                .createQuery("SELECT uqea FROM UserQuestionExtendedAnswer uqea where uqea.user.id = :userId")
                .setParameter("userId", user.getId())
                .list();
    }
}
