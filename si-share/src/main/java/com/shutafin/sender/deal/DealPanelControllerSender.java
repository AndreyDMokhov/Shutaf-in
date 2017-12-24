package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DealPanelControllerSender {

    private static String dealUrl;

    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    public DealPanelControllerSender(DiscoveryRoutingService discoveryRoutingService) {
        this.discoveryRoutingService = discoveryRoutingService;
        dealUrl = this.discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/panel/";
    }

    public DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb, Long userId) {
        dealPanelWeb.setUserId(userId);
        return new RestTemplate().postForEntity(dealUrl, dealPanelWeb, DealPanelResponse.class).getBody();
    }

    public DealPanelResponse getDealPanel(Long dealPanelId, Long userId) {
        String requestUrl = dealUrl + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("panelId", dealPanelId);
        return new RestTemplate().getForEntity(requestUrl, DealPanelResponse.class, uriVariables).getBody();
    }

    public DealPanelResponse renameDealPanel(Long dealPanelId, Long userId, NewTitleWeb newTitle) {
        String requestUrl = dealUrl + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("panelId", dealPanelId);
        return new RestTemplate().postForEntity(requestUrl, newTitle, DealPanelResponse.class, uriVariables).getBody();
    }

    public void deleteDealPanel(Long dealPanelId, Long userId) {
        String requestUrl = dealUrl + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("panelId", dealPanelId);
        new RestTemplate().delete(requestUrl, uriVariables);
    }
    
}
