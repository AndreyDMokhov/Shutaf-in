package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Transactional
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;


    @Override
    @Transactional(readOnly = true)
    //todo MS-MATCHING
    public List<User> findMatchingUsers(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        String matchingUrl = discoveryRoutingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/search/%d", user.getId());

        List body = new RestTemplate().getForEntity(matchingUrl, List.class).getBody();
        String accountsUrl = discoveryRoutingService.getRoute(RouteDirection.SI_ACCOUNT);

        return null;
    }

    @Override
    public void saveQuestionsAnswers(User user, List<UserQuestionAnswerDTO> questionsAnswers) {
        String url = discoveryRoutingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/save/%d", user.getId());
        new RestTemplate().put(url, questionsAnswers, new HashMap<>());
    }

    @Override
    public List<QuestionsListWithAnswersDTO> getUserQuestionsAnswers(Language language) {
        String url = discoveryRoutingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/questionnaire/initialization/%d", language.getId());

        return new RestTemplate().getForEntity(url, List.class).getBody();
    }

    @Override
    public List<MatchingQuestionsSelectedAnswersDTO> getUserQuestionsSelectedAnswers(User user) {
        String url = discoveryRoutingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("matching//questionnaire/answers/%d", user.getId());

        return new RestTemplate().getForEntity(url, List.class).getBody();
    }
}
