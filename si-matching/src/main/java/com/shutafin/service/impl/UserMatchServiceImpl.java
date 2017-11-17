package com.shutafin.service.impl;

import com.shutafin.model.infrastructure.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.Answer;
import com.shutafin.model.entities.Question;
import com.shutafin.model.infrastructure.AnswerElement;
import com.shutafin.model.infrastructure.SelectedAnswerElement;
import com.shutafin.model.match.UserExamKey;
import com.shutafin.model.match.VarietyExamKey;
import com.shutafin.model.infrastructure.AnswersForQuestion;
import com.shutafin.model.DTO.UserQuestionAnswerDTO;
import com.shutafin.model.DTO.QuestionsListWithSelectedAnswersDTO;
import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;
import com.shutafin.repository.*;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserExamKeyRepository userExamKeyRepository;

    @Autowired
    private VarietyExamKeyRepository varietyExamKeyRepository;

    @Autowired
    private QuestionLocaleRepository questionLocaleRepository;

    @Autowired
    private AnswerLocaleRepository answerLocaleRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Long> findMatchingUsers(User user) {
        List<Long> matchingUsersList = new ArrayList<>();
        List<UserExamKey> userExamKeyList;
        if (user == null) {
            return matchingUsersList;
        }

        UserExamKey userExamKey = userExamKeyRepository.findByUserId(user.getUserId());
        List<VarietyExamKey> varietyExamKeys = varietyExamKeyRepository.findAll();
        List<String> keys = getRegExpKeysForMatch(userExamKey.getExamKeyRegExp(), varietyExamKeys);
        userExamKeyList = userExamKeyRepository.findAllByExamKeyIn(keys);
        matchingUsersList = userExamKeyList.stream().map(UserExamKey::getUserId).collect(Collectors.toList());
        matchingUsersList.remove(user.getUserId());

        return matchingUsersList;
    }

    public List<String> getRegExpKeysForMatch(String examKeyRegExp, List<VarietyExamKey> varietyExamKey) {
        Set<String> res = new HashSet<>();

        List<String> keys = varietyExamKey.stream()
                .map(VarietyExamKey::getUserExamKey)
                .collect(Collectors.toList());

        for (String key : keys) {
            if (examKeyRegExp.contains("\\d+")) {
                if (examKeyRegExp.replaceAll("\\\\d\\+", "0").matches(key)
                        || key.replaceAll("\\\\d\\+", "0").matches(examKeyRegExp)) {
                    res.add(key);
                }
            } else {
                if (examKeyRegExp.matches(key)) {
                    res.add(key);
                }
            }
        }
        return new ArrayList<>(res);
    }


    @Override
    @Transactional
    public void saveQuestionsAnswers(User user, List<UserQuestionAnswerDTO> questionsAnswers) {
        userQuestionAnswerRepository.deleteAllByUserId(user.getUserId());

        userExamKeyRepository.deleteByUserId(user.getUserId());
        userQuestionAnswerRepository.saveList(user.getUserId(), questionsAnswers);

        List<String> examKeyRes = generateExamKey(questionsAnswers);
        userExamKeyRepository.save(new UserExamKey(user.getUserId(), examKeyRes.get(0), examKeyRes.get(1)));

        for (String str : examKeyRes) {
            if (varietyExamKeyRepository.findByUserExamKey(str) == null) {
                varietyExamKeyRepository.save(new VarietyExamKey(str));
            }
        }
    }

    private List<String> generateExamKey(List<UserQuestionAnswerDTO> questionsAnswers) {
        List<Integer> answersIsUniversalTrue = answerRepository.findAllAnswerIdByIsUniversalTrue();
        List<String> res = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        StringBuilder sbKeyRegExp = new StringBuilder();
        for (UserQuestionAnswerDTO questionAnswer : questionsAnswers) {
            sbKey.append("-Q" + questionAnswer.getQuestionId() + "_" + questionAnswer.getAnswerId());

            String answerKey = answersIsUniversalTrue.contains(questionAnswer.getAnswerId()) ? "\\d+" : questionAnswer.getAnswerId().toString();
            sbKeyRegExp.append("-Q" + questionAnswer.getQuestionId() + "_" + answerKey);
        }
        res.add(sbKey.toString());
        res.add(sbKeyRegExp.toString());

        return res;
    }

    @Override
    @Transactional
    public List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(User user) {
        Integer languageId = user.getLanguageId();
        List<QuestionsListWithAnswersDTO> result = questionLocaleRepository.findByLanguageId(languageId);
        List<AnswerElement> answers = answerLocaleRepository.findAllByLanguageId(languageId);

        for (QuestionsListWithAnswersDTO questionsListWithAnswers : result) {
            questionsListWithAnswers.setAnswers(answers.stream()
                    .filter(a -> Objects.equals(
                            a.getQuestionId(),
                            questionsListWithAnswers.getQuestionId()))
                    .map(a -> new AnswersForQuestion(
                            a.getAnswerId(),
                            a.getDescription(),
                            a.getIsUniversal()))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    @Transactional
    public List<QuestionsListWithSelectedAnswersDTO> getUserQuestionsSelectedAnswers(User user) {
        List<QuestionsListWithSelectedAnswersDTO> result = new ArrayList<>();
        List<SelectedAnswerElement> selectedQuestionsAnswers = userQuestionAnswerRepository.findAllByUserId(user.getUserId());

        selectedQuestionsAnswers.stream()
                .collect(Collectors.groupingBy(
                        SelectedAnswerElement::getQuestionId,
                        Collectors.mapping(SelectedAnswerElement::getAnswerId, Collectors.toList())))
                .forEach((key, value) -> result.add(new QuestionsListWithSelectedAnswersDTO(key, value)));

        return result;
    }
}
