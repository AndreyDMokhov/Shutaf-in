package com.shutafin.service.deal.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.DealUserWeb;
import com.shutafin.model.web.deal.DealWeb;
import com.shutafin.model.web.deal.NewTitleWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.InternalRestTemplateService;
import com.shutafin.service.deal.DealService;
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
public class DealServiceImpl implements DealService {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private InternalRestTemplateService internalRestTemplateService;

    @Override
    public DealWeb initiateDeal(DealWeb dealWeb, User user) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
        dealWeb.setOriginUserId(user.getId());
        return internalRestTemplateService.getResponse(HttpMethod.POST, dealUrl, null, dealWeb, DealWeb.class);
    }

    @Override
    public void confirmDealUser(Long dealId, User user) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        internalRestTemplateService.getResponse(HttpMethod.PUT, requestUrl, uriVariables, null, null);
    }

    @Override
    public List<DealUserWeb> getAllUserDeals(User user) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
        String requestUrl = dealUrl + "all/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        return (List<DealUserWeb>) internalRestTemplateService.getListResponse(HttpMethod.GET, requestUrl, uriVariables,
                null, DealUserWeb.class);
    }

    @Override
    public DealResponse getDeal(Long dealId) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
        String requestUrl = dealUrl + "{dealId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        return internalRestTemplateService.getResponse(HttpMethod.GET, requestUrl, uriVariables, null,
                DealResponse.class);
    }

    @Override
    public DealWeb renameDeal(Long dealId, User user, NewTitleWeb newTitleWeb) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
        String requestUrl = dealUrl + "rename/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        return internalRestTemplateService.getResponse(HttpMethod.POST, requestUrl, uriVariables, newTitleWeb,
                DealWeb.class);
    }

    @Override
    public void deleteDeal(Long dealId, User user) {
        String dealUrl = discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        internalRestTemplateService.getResponse(HttpMethod.DELETE, requestUrl, uriVariables, null, null);
    }

}
