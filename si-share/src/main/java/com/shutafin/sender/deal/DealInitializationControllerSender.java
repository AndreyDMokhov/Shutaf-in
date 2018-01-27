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

    @Autowired
    private RestTemplate restTemplate;

    public DealInitializationResponse getDealInitializationResponse() {
        String requestUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/initialization";
        return restTemplate.getForEntity(requestUrl, DealInitializationResponse.class).getBody();
    }
}
