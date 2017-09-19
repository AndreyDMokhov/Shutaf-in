package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.UserQuestionAnswerCity;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.model.entities.match.VarietyExamKey;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.user.QuestionAnswerCityRequest;
import com.shutafin.model.web.user.QuestionAnswerRequest;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.common.UserExamKeyRepository;
import com.shutafin.repository.common.UserQuestionAnswerCityRepository;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import com.shutafin.repository.common.VarietyExamKeyRepository;
import com.shutafin.repository.initialization.locale.AnswerRepository;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import com.shutafin.service.UserMatchService;
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
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserExamKeyRepository userExamKeyRepository;

    @Autowired
    private VarietyExamKeyRepository varietyExamKeyRepository;

    @Autowired
    private UserQuestionAnswerCityRepository userQuestionAnswerCityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findMatchingUsers(User user) {
        List<User> matchingUsersList = new ArrayList<>();

        if (user == null) {
            return matchingUsersList;
        }

        //match users by MUST questions - Filter_1
        UserExamKey userExamKey = userExamKeyRepository.getUserExamKey(user);
        List<String> keys = varietyExamKeyRepository.getKeysForMatch(userExamKey.getExamKeyRegExp());
        matchingUsersList = userExamKeyRepository.getMatchedUsers(keys);
        matchingUsersList.remove(user);

        //Call chain of responsibility

        //match users from Filter_1 by City questions - Filter_2
//        List<User> usersByCity = userQuestionAnswerCityRepository.getAllMatchedUsers(user);
//        matchingUsersList = innerJoinUserLists(matchingUsersList, usersByCity);


        return matchingUsersList;
    }

    private List<User> innerJoinUserLists(List<User> matchedUsersList, List<User> additionalUsersFilterList) {
        List<User> result = new ArrayList<>();
        for (User user : additionalUsersFilterList) {
            if(matchedUsersList.contains(user)){
                result.add(user);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void saveQuestionsAnswers(User user, List<QuestionAnswerRequest> questionsAnswers) {

        userQuestionAnswerRepository.deleteUserAnswers(user);
        userExamKeyRepository.delete(user);

        TreeMap<Question, Answer> questionAnswerMap = new TreeMap<>();
        for (QuestionAnswerRequest questionAnswer : questionsAnswers) {
            Question question = questionRepository.findById(questionAnswer.getQuestionId());
            Answer answer = answerRepository.findById(questionAnswer.getAnswerId());
            userQuestionAnswerRepository.save(new UserQuestionAnswer(user, question, answer));

            questionAnswerMap.put(question, answer);
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

    @Override
    @Transactional
    public void saveUserQuestionsCityAnswers(User user, List<QuestionAnswerCityRequest> questionsCityAnswers) {
        userQuestionAnswerCityRepository.deleteUserCityAnswers(user);
        for (QuestionAnswerCityRequest questionAnswerCityRequest : questionsCityAnswers) {
            Question question = questionRepository.findById(questionAnswerCityRequest.getQuestionId());
            City city = cityRepository.findById(questionAnswerCityRequest.getCityId());

            userQuestionAnswerCityRepository.save(new UserQuestionAnswerCity(user, question, city));
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
    public List<QuestionResponse> getUserQuestionsAnswers(User user) {
        return questionRepository.getUserQuestionsAnswers(userAccountRepository.findUserLanguage(user));
    }

    @Override
    @Transactional
    public List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user) {
        return questionRepository.getUserQuestionsSelectedAnswers(user);
    }
}
