package com.shutafin.sender.matching;

import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class UserMatchingScoreControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public Map<Long, Integer> getUserMatchingScores(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/%d", userId);

        return new RestTemplate().getForEntity(url, Map.class).getBody();
    }

    public void addUserQuestionExtendedAnswers(Long userId, List<UserQuestionExtendedAnswersWeb> questionExtendedAnswersWeb) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/%d", userId);
        new RestTemplate().postForEntity(url, questionExtendedAnswersWeb, Void.class);
    }
}
