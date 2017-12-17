package com.shutafin.service.deal.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.*;
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

    private static String dealUrl;

    private DiscoveryRoutingService discoveryRoutingService;
    private InternalRestTemplateService internalRestTemplateService;

    @Autowired
    public DealServiceImpl(DiscoveryRoutingService discoveryRoutingService,
                           InternalRestTemplateService internalRestTemplateService) {
        this.discoveryRoutingService = discoveryRoutingService;
        this.internalRestTemplateService = internalRestTemplateService;
        dealUrl = this.discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
    }

    @Override
    public DealWeb initiateDeal(DealWeb dealWeb, User user) {
        InternalDealWeb internalDealWeb = new InternalDealWeb();
        internalDealWeb.setOriginUserId(user.getId());
        internalDealWeb.setTitle(dealWeb.getTitle());
        internalDealWeb.setUsers(dealWeb.getUsers());
        internalDealWeb = internalRestTemplateService.getResponse(HttpMethod.POST, dealUrl, null, internalDealWeb,
                InternalDealWeb.class);
        dealWeb.setDealId(internalDealWeb.getDealId());
        dealWeb.setTitle(internalDealWeb.getTitle());
        return dealWeb;
    }

    @Override
    public void confirmDealUser(Long dealId, User user) {
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        internalRestTemplateService.getResponse(HttpMethod.PUT, requestUrl, uriVariables, null, null);
    }

    @Override
    public void leaveDeal(Long dealId, User user) {
        String requestUrl = dealUrl + "leave/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        internalRestTemplateService.getResponse(HttpMethod.PUT, requestUrl, uriVariables, null, null);
    }

    @Override
    public void removeDealUser(Long dealId, User userOrigin, Long userToRemoveId) {
        String requestUrl = dealUrl + "remove";
        InternalDealRemoveUserWeb internalDealRemoveUserWeb = new InternalDealRemoveUserWeb(dealId, userOrigin.getId(),
                userToRemoveId);
        internalRestTemplateService.getResponse(HttpMethod.PUT, requestUrl, null, internalDealRemoveUserWeb,
                null);
    }

    @Override
    public List<DealUserWeb> getAllUserDeals(User user) {
        String requestUrl = dealUrl + "all/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", user.getId());
        return (List<DealUserWeb>) internalRestTemplateService.getListResponse(HttpMethod.GET, requestUrl, uriVariables,
                null, DealUserWeb.class);
    }

    @Override
    public DealResponse getDeal(Long dealId, User user) {
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        return internalRestTemplateService.getResponse(HttpMethod.GET, requestUrl, uriVariables, null,
                DealResponse.class);
    }

    @Override
    public DealWeb renameDeal(Long dealId, User user, NewTitleWeb newTitleWeb) {
        String requestUrl = dealUrl + "rename/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        return internalRestTemplateService.getResponse(HttpMethod.POST, requestUrl, uriVariables, newTitleWeb,
                DealWeb.class);
    }

    @Override
    public void deleteDeal(Long dealId, User user) {
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", user.getId());
        internalRestTemplateService.getResponse(HttpMethod.DELETE, requestUrl, uriVariables, null, null);
    }

}
