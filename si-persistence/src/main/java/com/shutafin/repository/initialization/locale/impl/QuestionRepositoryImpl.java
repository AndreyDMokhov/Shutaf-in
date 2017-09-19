package com.shutafin.repository.initialization.locale.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.AnswerResponse;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerRequest;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 8/22/2017.
 */
@Repository
public class QuestionRepositoryImpl extends AbstractConstEntityDao<Question> implements QuestionRepository {

    @Override
    public List<QuestionResponse> getUserQuestionsAnswers(Language language) {

        StringBuilder hql = new StringBuilder()
                .append("select new com.shutafin.model.web.QuestionResponse ")
                .append(" ( ")
                .append(" cl.question.id, ")
                .append(" cl.description, ")
                .append(" cl.question.isActive ")
                .append(" )")
                .append(" from QuestionLocale cl where cl.language = :language AND cl.question.isActive = 1  ");

        List<QuestionResponse> result = getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();

        hql = new StringBuilder()
                .append("select new com.shutafin.repository.initialization.locale.impl.QuestionRepositoryImpl$QuestionAnswerElement ")
                .append(" ( ")
                .append(" cl.answer.question.id, ")
                .append(" cl.answer.id, ")
                .append(" cl.description, ")
                .append(" cl.answer.isUniversal ")
                .append(" ) ")
                .append(" from AnswerLocale cl where cl.language = :language  ");

        List<QuestionAnswerElement> answers = getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("language", language)
                .getResultList();

        Map<Integer, List<AnswerResponse>> questionAnswers = new HashMap<>();
        for (QuestionAnswerElement questionAnswerElement : answers) {
            if (!questionAnswers.containsKey(questionAnswerElement.getQuestionId())) {
                questionAnswers.put(questionAnswerElement.getQuestionId(), new ArrayList<>());
            }
            questionAnswers.get(questionAnswerElement.getQuestionId()).add(new AnswerResponse(questionAnswerElement.getAnswerId(), questionAnswerElement.getDescription(), questionAnswerElement.getIsUniversal()));
        }

        for (QuestionResponse questionResponse : result) {
            questionResponse.setAnswers(questionAnswers.get(questionResponse.getQuestionId()));
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

        List<QuestionAnswerRequest> selectedQustionsAnswers = getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .setParameter("user", user)
                .getResultList();

        Map<Integer, List<Integer>> questionAnswers = new HashMap<>();
        for (QuestionAnswerRequest element : selectedQustionsAnswers) {
            if (!questionAnswers.containsKey(element.getQuestionId())) {
                questionAnswers.put(element.getQuestionId(), new ArrayList<>());
            }
            questionAnswers.get(element.getQuestionId()).add(element.getAnswerId());
        }

        for (QuestionAnswerRequest element : selectedQustionsAnswers) {
            result.add(new QuestionSelectedAnswersResponse(element.getQuestionId(), questionAnswers.get(element.getQuestionId())));
        }

        return result;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    static class QuestionAnswerElement {
        private Integer questionId;
        private Integer answerId;
        private String description;
        private Boolean isUniversal;
    }

}
