package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.DealInitializationResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DealInitializationControllerSender {

    private static String dealUrl;

    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    public DealInitializationControllerSender(DiscoveryRoutingService discoveryRoutingService) {
        this.discoveryRoutingService = discoveryRoutingService;
        dealUrl = this.discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
    }
    public DealInitializationResponse getDealInitializationResponse() {
        String requestUrl = dealUrl + "initialization";
        return new RestTemplate().getForEntity(requestUrl, DealInitializationResponse.class).getBody();
    }
}
