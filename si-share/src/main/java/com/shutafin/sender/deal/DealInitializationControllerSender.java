package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.DealInitializationResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DealInitializationControllerSender {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    public DealInitializationResponse getDealInitializationResponse() {
        String requestUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/internal/deal/initialization";
        return new RestTemplate().getForEntity(requestUrl, DealInitializationResponse.class).getBody();
    }
}
