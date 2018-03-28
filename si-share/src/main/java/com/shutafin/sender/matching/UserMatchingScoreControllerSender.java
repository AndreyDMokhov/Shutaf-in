package com.shutafin.sender.matching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.matching.UserBaseResponse;
import com.shutafin.model.web.matching.UserMatchingScoreDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class UserMatchingScoreControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public Map<Long, Integer> getUserMatchingScores(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/%d", userId);

        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();
        return new ObjectMapper().readValue(jsonBody, new TypeReference<Map<Long, Integer>>() {
        });
    }

    @SneakyThrows
    public List<UserSearchResponse> getMatchedUserSearchResponses(Long userId, Integer page, Integer results, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/search/%d?page=%d&results=%d", userId, page, results);

        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {});
    }

    @SneakyThrows
    public List<UserBaseResponse> getMatchedUserBaseResponses(Long userId, Integer page, Integer results, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/base/%d?page=%d&results=%d", userId, page, results);

        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserBaseResponse>>() {});
    }

    public void addUserQuestionExtendedAnswers(Long userId, List<UserQuestionExtendedAnswersWeb> questionExtendedAnswersWeb) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/%d", userId);
        restTemplate.postForEntity(url, questionExtendedAnswersWeb, Void.class);
    }

    public UserMatchingScoreDTO getOneMatchingScore(Long userOrigin, Long userToMatch) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/delete/%d/%d", userOrigin, userToMatch);
        return restTemplate.getForEntity(url, UserMatchingScoreDTO.class).getBody();
    }

    public Long deleteUserMatchingScores(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/delete/%d", userId);
        return restTemplate.getForEntity(url, Long.class).getBody();
    }
}
