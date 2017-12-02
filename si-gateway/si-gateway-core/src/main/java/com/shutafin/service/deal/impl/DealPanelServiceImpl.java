package com.shutafin.service.deal.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealDocumentWeb;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.InternalRestTemplateService;
import com.shutafin.service.deal.DealPanelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class DealPanelServiceImpl implements DealPanelService {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private InternalRestTemplateService internalRestTemplateService;

    @Override
    public DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb, User user) {
        dealPanelWeb.setUserId(user.getId());
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/panel/";
        return internalRestTemplateService.getResponse(HttpMethod.POST, dealUrl, null, dealPanelWeb,
                DealPanelResponse.class);
    }

    @Override
    public DealPanelResponse getDealPanel(Long dealPanelId, User user) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/panel/";
        String requestUrl = dealUrl + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        uriVariables.put("panelId", dealPanelId);
        return internalRestTemplateService.getResponse(HttpMethod.GET, requestUrl, uriVariables, null,
                DealPanelResponse.class);
    }

    @Override
    public DealPanelResponse renameDealPanel(Long dealPanelId, User user, NewTitleWeb newTitle) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/panel/";
        String requestUrl = dealUrl + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        uriVariables.put("panelId", dealPanelId);
        return internalRestTemplateService.getResponse(HttpMethod.POST, requestUrl, uriVariables, newTitle,
                DealPanelResponse.class);
    }

    @Override
    public void deleteDealPanel(Long dealPanelId, User user) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/panel/";
        String requestUrl = dealUrl + "{userId}/{panelId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        uriVariables.put("panelId", dealPanelId);
        internalRestTemplateService.getResponse(HttpMethod.DELETE, requestUrl, uriVariables, null, null);
    }

}
