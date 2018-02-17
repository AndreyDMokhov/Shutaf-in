package com.shutafin.service.impl;

import com.shutafin.model.infrastructure.AnswerElement;
import com.shutafin.model.infrastructure.SelectedAnswerElement;
import com.shutafin.model.match.UserExamKey;
import com.shutafin.model.match.VarietyExamKey;
import com.shutafin.model.web.matching.AnswersForQuestion;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.repository.*;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    private UserQuestionAnswerRepository userQuestionAnswerRepository;
    private AnswerRepository answerRepository;
    private VarietyExamKeyRepository varietyExamKeyRepository;
    private QuestionLocaleRepository questionLocaleRepository;
    private AnswerLocaleRepository answerLocaleRepository;
    private UserExamKeyRepository userExamKeyRepository;

    @Autowired
    public UserMatchServiceImpl(UserQuestionAnswerRepository userQuestionAnswerRepository, AnswerRepository answerRepository, VarietyExamKeyRepository varietyExamKeyRepository, QuestionLocaleRepository questionLocaleRepository, AnswerLocaleRepository answerLocaleRepository, UserExamKeyRepository userExamKeyRepository) {
        this.userQuestionAnswerRepository = userQuestionAnswerRepository;
        this.answerRepository = answerRepository;
        this.varietyExamKeyRepository = varietyExamKeyRepository;
        this.questionLocaleRepository = questionLocaleRepository;
        this.answerLocaleRepository = answerLocaleRepository;
        this.userExamKeyRepository = userExamKeyRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Long> findMatchingUsers(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        //match users by MUST questions
        UserExamKey userExamKey = userExamKeyRepository.findByUserId(userId);

        if (userExamKey == null) {
            return new ArrayList<>();
        }

        List<VarietyExamKey> varietyExamKeys = varietyExamKeyRepository.findAll();
        List<String> keys = getRegExpKeysForMatch(userExamKey.getExamKeyRegExp(), varietyExamKeys);
        List<Long> matchingUsersList = userExamKeyRepository.getMatchedUsers(keys);
        matchingUsersList.remove(userId);

        return matchingUsersList;
    }

    private List<String> getRegExpKeysForMatch(String examKeyRegExp, List<VarietyExamKey> varietyExamKey) {
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
    public void saveSelectedUserQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers) {
        userQuestionAnswerRepository.deleteAllByUserId(userId);

        userExamKeyRepository.deleteByUserId(userId);
        userQuestionAnswerRepository.saveList(userId, questionsAnswers);

        List<String> examKeyRes = generateExamKey(questionsAnswers);
        userExamKeyRepository.save(new UserExamKey(userId, examKeyRes.get(0), examKeyRes.get(1), true));

        for (String str : examKeyRes) {
            if (varietyExamKeyRepository.getFirstByUserExamKey(str) == null) {
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
    public List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(Integer languageId) {
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
    public List<MatchingQuestionsSelectedAnswersDTO> getSelectedUserQuestionsAnswers(Long userId) {
        List<MatchingQuestionsSelectedAnswersDTO> result = new ArrayList<>();
        List<SelectedAnswerElement> selectedQuestionsAnswers = userQuestionAnswerRepository.findAllByUserId(userId);

        selectedQuestionsAnswers.stream()
                .collect(Collectors.groupingBy(
                        SelectedAnswerElement::getQuestionId,
                        Collectors.mapping(SelectedAnswerElement::getAnswerId, Collectors.toList())))
                .forEach((key, value) -> result.add(new MatchingQuestionsSelectedAnswersDTO(key, value)));

        return result;
    }

    @Override
    @Transactional
    public void setIsUserMatchingEnabled(Long userId, Boolean isEnabled) {
        UserExamKey userExamKey = userExamKeyRepository.findByUserId(userId);
        if (userExamKey != null){
            userExamKey.setIsMatchingEnabled(isEnabled);
        }
    }

    @Override
    public Boolean getIsMatchingEnabled(Long userId) {
        UserExamKey userExamKey = userExamKeyRepository.findByUserId(userId);
        if (userExamKey != null){
            return userExamKey.getIsMatchingEnabled();
        }
        return null;
    }
}
