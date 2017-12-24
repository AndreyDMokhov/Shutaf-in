package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.*;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DealControllerSender {

    private static String dealUrl;

    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    public DealControllerSender(DiscoveryRoutingService discoveryRoutingService) {
        this.discoveryRoutingService = discoveryRoutingService;
        dealUrl = this.discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
    }

    public DealWeb initiateDeal(DealWeb dealWeb, Long userId) {
        InternalDealWeb internalDealWeb = new InternalDealWeb();
        internalDealWeb.setOriginUserId(userId);
        internalDealWeb.setTitle(dealWeb.getTitle());
        internalDealWeb.setUsers(dealWeb.getUsers());
        internalDealWeb = new RestTemplate().postForEntity(dealUrl, internalDealWeb, InternalDealWeb.class).getBody();
        dealWeb.setDealId(internalDealWeb.getDealId());
        dealWeb.setTitle(internalDealWeb.getTitle());
        return dealWeb;
    }

    public void confirmDealUser(Long dealId, Long userId) {
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        new RestTemplate().put(requestUrl, null, uriVariables);
    }

    public void leaveDeal(Long dealId, Long userId) {
        String requestUrl = dealUrl + "leave/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        new RestTemplate().put(requestUrl, null, uriVariables);
    }

    public void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId) {
        String requestUrl = dealUrl + "remove";
        InternalDealRemoveUserWeb internalDealRemoveUserWeb = new InternalDealRemoveUserWeb(dealId, userOriginId,
                userToRemoveId);
        new RestTemplate().put(requestUrl, internalDealRemoveUserWeb);
    }

    public List<DealUserWeb> getAllUserDeals(Long userId) {
        String requestUrl = dealUrl + "all/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        return (List<DealUserWeb>) new RestTemplate().exchange(requestUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<?>>() {}, uriVariables).getBody();
    }

    public DealResponse getDeal(Long dealId, Long userId) {
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        return new RestTemplate().getForEntity(requestUrl, DealResponse.class, uriVariables).getBody();
    }

    public DealWeb renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb) {
        String requestUrl = dealUrl + "rename/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        return new RestTemplate().postForEntity(requestUrl, newTitleWeb, DealWeb.class, uriVariables).getBody();
    }

    public void deleteDeal(Long dealId, Long userId) {
        String requestUrl = dealUrl + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        new RestTemplate().delete(requestUrl, uriVariables);
    }
    
}
