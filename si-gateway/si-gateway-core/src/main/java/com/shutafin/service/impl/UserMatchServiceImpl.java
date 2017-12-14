package com.shutafin.service.impl;

import com.shutafin.model.entities.*;
import com.shutafin.model.entities.infrastructure.*;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.model.entities.match.VarietyExamKey;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerRequest;
import com.shutafin.repository.common.*;
import com.shutafin.repository.initialization.locale.*;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by evgeny on 8/12/2017.
 */
@Deprecated
@Service
@Transactional
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserExamKeyRepository userExamKeyRepository;

    @Autowired
    private VarietyExamKeyRepository varietyExamKeyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findMatchingUsers(User user) {

        if (user == null) {
            return new ArrayList<>();
        }

        //match users by MUST questions
        UserExamKey userExamKey = userExamKeyRepository.findByUser(user);

        if (userExamKey == null) {
            return new ArrayList<>();
        }

        List<String> keys = varietyExamKeyRepository.getKeysForMatch(userExamKey.getExamKeyRegExp());
        List<User> matchingUsersList = userExamKeyRepository.getMatchedUsers(keys);
        matchingUsersList.remove(user);

        return matchingUsersList;
    }

    @Override
    @Transactional
    public void saveQuestionsAnswers(User user, List<QuestionAnswerRequest> questionsAnswers) {

        userQuestionAnswerRepository.deleteAllByUser(user);
        userExamKeyRepository.deleteByUser(user);

        TreeMap<Question, Answer> questionAnswerMap = new TreeMap<>();
        for (QuestionAnswerRequest questionAnswer : questionsAnswers) {
            Question question = questionRepository.findOne(questionAnswer.getQuestionId());

            Answer answer = answerRepository.findOne(questionAnswer.getAnswerId());
            userQuestionAnswerRepository.save(new UserQuestionAnswer(user, question, answer));
            questionAnswerMap.put(question, answer);
        }

        List<String> examKeyRes = generateExamKey(questionAnswerMap);
        userExamKeyRepository.save(new UserExamKey(user, examKeyRes.get(0), examKeyRes.get(1)));
        if (varietyExamKeyRepository.findByUserExamKey(examKeyRes.get(0)) == null) {
            varietyExamKeyRepository.save(new VarietyExamKey(examKeyRes.get(0)));
        }
        if (varietyExamKeyRepository.findByUserExamKey(examKeyRes.get(1)) == null) {
            varietyExamKeyRepository.save(new VarietyExamKey(examKeyRes.get(1)));
        }
    }

    private List<String> generateExamKey(NavigableMap<Question, Answer> sortedQuestionsAnswers) {
        List<String> res = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        StringBuilder sbKeyRegExp = new StringBuilder();
        for (Map.Entry<Question, Answer> entry : sortedQuestionsAnswers.entrySet()) {
            sbKey.append("-Q" + entry.getKey().getId() + "_" + entry.getValue().getId());

            String answer = entry.getValue().getIsUniversal() ? "\\d+" : entry.getValue().getId().toString();
            sbKeyRegExp.append("-Q" + entry.getKey().getId() + "_" + answer);
        }
        res.add(sbKey.toString());
        res.add(sbKeyRegExp.toString());

        return res;
    }

    @Override
    @Transactional
    public List<QuestionAnswersResponse> getUserQuestionsAnswers(Language language) {
        return questionRepository.getUserQuestionsAnswers(language);
    }

    @Override
    @Transactional
    public List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user) {
        return questionRepository.getUserQuestionsSelectedAnswers(user);
    }
}
