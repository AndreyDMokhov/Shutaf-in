package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.UserQuestionAnswerCity;
import com.shutafin.model.entities.UserQuestionAnswerGender;
import com.shutafin.model.entities.infrastructure.*;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.model.entities.match.VarietyExamKey;
import com.shutafin.model.entities.types.AnswerType;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerRequest;
import com.shutafin.repository.common.*;
import com.shutafin.repository.initialization.locale.AnswerRepository;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.match.UsersMatchChain;
import com.shutafin.service.match.impl.UsersMatchByCity;
import com.shutafin.service.match.impl.UsersMatchByGender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by evgeny on 8/12/2017.
 */
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

    @Autowired
    private UserQuestionAnswerCityRepository userQuestionAnswerCityRepository;

    @Autowired
    private UserQuestionAnswerGenderRepository userQuestionAnswerGenderRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findMatchingUsers(User user) {

        if (user == null) {
            return new ArrayList<>();
        }

        //match users by MUST questions - Filter_1
        UserExamKey userExamKey = userExamKeyRepository.getUserExamKey(user);

        if (userExamKey == null) {
            return new ArrayList<>();
        }

        List<String> keys = varietyExamKeyRepository.getKeysForMatch(userExamKey.getExamKeyRegExp());
        List<User> matchingUsersList = userExamKeyRepository.getMatchedUsers(keys);
        matchingUsersList.remove(user);

        //Call chain of responsibility
        UsersMatchChain usersMatchChain = createMatchChain(user);
        matchingUsersList = usersMatchChain.doMatch(matchingUsersList);

        return matchingUsersList;
    }

    private UsersMatchChain createMatchChain(User user) {
        UsersMatchChain genderMatch = new UsersMatchByGender(user, userQuestionAnswerGenderRepository);

        UsersMatchChain cityrMatch = new UsersMatchByCity(user, userQuestionAnswerCityRepository);
        genderMatch.setNext(cityrMatch);

        return genderMatch;
    }

    @Override
    @Transactional
    public void saveQuestionsAnswers(User user, List<QuestionAnswerRequest> questionsAnswers) {

        userQuestionAnswerRepository.deleteUserAnswers(user);
        userQuestionAnswerCityRepository.deleteUserCityAnswers(user);
        userQuestionAnswerGenderRepository.deleteUserGenderAnswers(user);
        userExamKeyRepository.delete(user);

        TreeMap<Question, Answer> questionAnswerMap = new TreeMap<>();
        for (QuestionAnswerRequest questionAnswer : questionsAnswers) {
            Question question = questionRepository.findById(questionAnswer.getQuestionId());

            if (question.getQuestionType() == AnswerType.STANDARD.getCode()){
                Answer answer = answerRepository.findById(questionAnswer.getAnswerId());
                userQuestionAnswerRepository.save(new UserQuestionAnswer(user, question, answer));
                questionAnswerMap.put(question, answer);
            } else if (question.getQuestionType() == AnswerType.CITY.getCode()){
                City city = cityRepository.findById(questionAnswer.getAnswerId());
                userQuestionAnswerCityRepository.save(new UserQuestionAnswerCity(user, question, city));
            } else if (question.getQuestionType() == AnswerType.GENDER.getCode()){
                Gender gender = genderRepository.findById(questionAnswer.getAnswerId());
                userQuestionAnswerGenderRepository.save(new UserQuestionAnswerGender(user, question, gender));
            }
        }

        List<String> examKeyRes = generateExamKey(questionAnswerMap);
        userExamKeyRepository.save(new UserExamKey(user, examKeyRes.get(0), examKeyRes.get(1)));
        if (varietyExamKeyRepository.findUserExamKeyByStr(examKeyRes.get(0)) == null) {
            varietyExamKeyRepository.save(new VarietyExamKey(examKeyRes.get(0)));
        }
        if (varietyExamKeyRepository.findUserExamKeyByStr(examKeyRes.get(1)) == null) {
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
