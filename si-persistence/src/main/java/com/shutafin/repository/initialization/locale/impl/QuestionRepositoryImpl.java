package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.AnswerResponse;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.initialization.QuestionAnswerElement;
import com.shutafin.model.web.user.QuestionAnswerRequest;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import com.shutafin.repository.initialization.locale.QuestionRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<QuestionAnswersResponse> getUserQuestionsAnswers(Language language) {
        StringBuilder hql = new StringBuilder()
                .append("select new com.shutafin.model.web.QuestionAnswersResponse ")
                .append(" ( ")
                .append(" cl.question.id, ")
                .append(" cl.description, ")
                .append(" cl.question.isActive ")
                .append(" )")
                .append(" from QuestionLocale cl where cl.language = :language AND cl.question.isActive = 1  ");

        List<QuestionAnswersResponse> result = entityManager
                .createQuery(hql.toString())
                .setHint("org.hibernate.cacheable", true)
                .setParameter("language", language)
                .getResultList();

        hql = new StringBuilder()
                .append("select new com.shutafin.model.web.initialization.QuestionAnswerElement ")
                .append(" ( ")
                .append(" cl.answer.question.id, ")
                .append(" cl.answer.id, ")
                .append(" cl.description, ")
                .append(" cl.answer.isUniversal ")
                .append(" ) ")
                .append(" from AnswerLocale cl where cl.language = :language  ");

        List<QuestionAnswerElement> answers = entityManager
                .createQuery(hql.toString())
                .setParameter("language", language)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();

        Map<Integer, List<AnswerResponse>> questionAnswers = new HashMap<>();
        for (QuestionAnswerElement questionAnswerElement : answers) {
            if (!questionAnswers.containsKey(questionAnswerElement.getQuestionId())) {
                questionAnswers.put(questionAnswerElement.getQuestionId(), new ArrayList<>());
            }
            questionAnswers.get(questionAnswerElement.getQuestionId()).add(new AnswerResponse(questionAnswerElement.getAnswerId(), questionAnswerElement.getDescription(), questionAnswerElement.getIsUniversal()));
        }

        for (QuestionAnswersResponse questionAnswersResponse : result) {
            questionAnswersResponse.setAnswers(questionAnswers.get(questionAnswersResponse.getQuestionId()));
        }

        return result;
    }

    @Override
    public List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user) {
        List<QuestionSelectedAnswersResponse> result = new ArrayList<>();

        StringBuilder hql = new StringBuilder()
                .append("select new com.shutafin.model.web.user.QuestionAnswerRequest ")
                .append(" ( ")
                .append(" cl.answer.question.id, ")
                .append(" cl.answer.id ")
                .append(" ) ")
                .append(" from UserQuestionAnswer cl where cl.user = :user  ");

        List<QuestionAnswerRequest> selectedQuestionsAnswers = entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();

        Map<Integer, List<Integer>> questionAnswers = new HashMap<>();
        for (QuestionAnswerRequest element : selectedQuestionsAnswers) {
            if (!questionAnswers.containsKey(element.getQuestionId())) {
                questionAnswers.put(element.getQuestionId(), new ArrayList<>());
            }
            questionAnswers.get(element.getQuestionId()).add(element.getAnswerId());
        }

        for (QuestionAnswerRequest element : selectedQuestionsAnswers) {
            result.add(new QuestionSelectedAnswersResponse(element.getQuestionId(), questionAnswers.get(element.getQuestionId())));
        }

        return result;
    }

}
