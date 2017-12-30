package com.shutafin.sender.matching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class UserMatchingScoreControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public Map<Long, Integer> getUserMatchingScores(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/%d", userId);
            return new ObjectMapper().convertValue(new RestTemplate().getForEntity(url, Map.class).getBody(), new TypeReference<Map<Long, Integer>>() {
            });
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void addUserQuestionExtendedAnswers(Long userId, List<UserQuestionExtendedAnswersWeb> questionExtendedAnswersWeb) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/%d", userId);
            new RestTemplate().postForEntity(url, questionExtendedAnswersWeb, Void.class);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public UserMatchingScoreDTO getOneMatchingScore(Long userOrigin, Long userToMatch) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/delete/%d/%d", userOrigin, userToMatch);
            return new RestTemplate().getForEntity(url, UserMatchingScoreDTO.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public Long deleteUserMatchingScores(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/delete/%d", userId);
            return new RestTemplate().getForEntity(url, Long.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
