package com.shutafin.sender.matching;

import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MatchingInitializationControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public MatchingInitializationResponse getInitializationResponse(Long userId, Integer languageId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/initialization/%d/all/%d", userId, languageId);

        return new RestTemplate().getForEntity(url, MatchingInitializationResponse.class).getBody();
    }
}
