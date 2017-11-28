package com.shutafin.service.impl;

import com.shutafin.model.entities.*;
import com.shutafin.model.entities.infrastructure.*;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.repository.common.*;
import com.shutafin.repository.initialization.locale.*;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Transactional
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserExamKeyRepository userExamKeyRepository;

    @Autowired
    private VarietyExamKeyRepository varietyExamKeyRepository;

    @Override
    @Transactional(readOnly = true)
    //todo MS-MATCHING
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
    public void saveQuestionsAnswers(User user, List<UserQuestionAnswerDTO> questionsAnswers) {

        String url = discoveryRoutingService.getRoute(RouteDirection.SI_MATCHING) + String.format("/matching/save/%d", user.getId());
        new RestTemplate().put(url, questionsAnswers, new HashMap<>());
    }

    @Override
    @Transactional
    //todo MS-MATCHING
    public List<QuestionAnswersResponse> getUserQuestionsAnswers(Language language) {
        return questionRepository.getUserQuestionsAnswers(language);
    }

    @Override
    @Transactional
    //todo MS-MATCHING
    public List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user) {
        return questionRepository.getUserQuestionsSelectedAnswers(user);
    }
}
