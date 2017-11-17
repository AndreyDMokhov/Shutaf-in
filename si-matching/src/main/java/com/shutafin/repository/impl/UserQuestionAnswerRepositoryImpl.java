package com.shutafin.repository.impl;

import com.shutafin.model.DTO.UserQuestionAnswerDTO;
import com.shutafin.model.entities.Answer;
import com.shutafin.model.entities.Question;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.infrastructure.SelectedAnswerElement;
import com.shutafin.repository.CustomUserQuestionAnswerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class UserQuestionAnswerRepositoryImpl implements CustomUserQuestionAnswerRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<SelectedAnswerElement> findAllByUserId(Long userId) {

        StringBuilder hql = new StringBuilder()
                .append("select new com.shutafin.model.infrastructure.SelectedAnswerElement ")
                .append("( cl.question.id, cl.answer.id ) ")
                .append("from UserQuestionAnswer cl where cl.userId = :userId ");

        Query query = em.createQuery(hql.toString());
        query.setParameter("userId", userId);

        return query.getResultList();

    }

    @Override
    public void saveList(Long userId, List<UserQuestionAnswerDTO> questionsAnswers) {
        for (UserQuestionAnswerDTO questionAnswer : questionsAnswers) {
            Question question = em.getReference(Question.class, questionAnswer.getQuestionId());
            Answer answer = em.getReference(Answer.class, questionAnswer.getAnswerId());

            UserQuestionAnswer userQuestionAnswer = new UserQuestionAnswer(userId, question, answer);
            em.persist(userQuestionAnswer);
        }
    }
}
