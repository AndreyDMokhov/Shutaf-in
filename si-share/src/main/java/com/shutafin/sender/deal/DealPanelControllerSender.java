package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DealPanelControllerSender {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private RestTemplate restTemplate;


    public DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb, Long userId) {
        dealPanelWeb.setUserId(userId);
        return restTemplate
                .postForEntity(getDealUrl(), dealPanelWeb, DealPanelResponse.class)
                .getBody();
    }

    public DealPanelResponse getDealPanel(Long dealPanelId, Long userId) {
        String requestUrl = getDealUrl() + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("panelId", dealPanelId);

        return restTemplate
                .getForEntity(requestUrl, DealPanelResponse.class, uriVariables)
                .getBody();
    }

    public DealPanelResponse renameDealPanel(Long dealPanelId, Long userId, DealTitleChangeWeb newTitle) {
        String requestUrl = getDealUrl() + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("panelId", dealPanelId);

        return restTemplate
                .postForEntity(requestUrl, newTitle, DealPanelResponse.class, uriVariables)
                .getBody();
    }

    public void deleteDealPanel(Long dealPanelId, Long userId) {
        String requestUrl = getDealUrl() + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        uriVariables.put("panelId", dealPanelId);

        restTemplate.delete(requestUrl, uriVariables);
    }
    
    private String getDealUrl() {
        return discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/panel/";
    }
}
