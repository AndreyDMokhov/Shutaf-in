package com.shutafin.sender.deal;

import com.shutafin.model.web.deal.*;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DealControllerSender {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    public DealWeb initiateDeal(DealWeb dealWeb, Long userId) {
        String requestUrl = getDealUrl();
        InternalDealWeb internalDealWeb = new InternalDealWeb();
        internalDealWeb.setOriginUserId(userId);
        internalDealWeb.setTitle(dealWeb.getTitle());
        internalDealWeb.setUsers(dealWeb.getUsers());
        internalDealWeb = new RestTemplate().postForEntity(requestUrl, internalDealWeb, InternalDealWeb.class).getBody();
        dealWeb.setDealId(internalDealWeb.getDealId());
        dealWeb.setTitle(internalDealWeb.getTitle());
        return dealWeb;
    }

    public void confirmDealUser(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        new RestTemplate().put(requestUrl, request, uriVariables);
    }

    public void leaveDeal(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "leave/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        new RestTemplate().put(requestUrl, request, uriVariables);
    }

    public void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId) {
        String requestUrl = getDealUrl() + "remove";
        InternalDealUserWeb internalDealUserWeb = new InternalDealUserWeb(dealId, userOriginId,
                userToRemoveId);
        new RestTemplate().put(requestUrl, internalDealUserWeb);
    }

    public void addDealUser(Long dealId, Long userOriginId, Long userToAddId) {
        String requestUrl = getDealUrl() + "add";
        InternalDealUserWeb internalDealUserWeb = new InternalDealUserWeb(dealId, userOriginId, userToAddId);
        new RestTemplate().put(requestUrl, internalDealUserWeb);
    }

    public List<DealUserWeb> getAllUserDeals(Long userId) {
        String requestUrl = getDealUrl() + "all/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);
        return (List<DealUserWeb>) new RestTemplate().exchange(requestUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<?>>() {}, uriVariables).getBody();
    }

    public DealResponse getDeal(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        return new RestTemplate().getForEntity(requestUrl, DealResponse.class, uriVariables).getBody();
    }

    public DealWeb renameDeal(Long dealId, Long userId, NewTitleWeb newTitleWeb) {
        String requestUrl = getDealUrl() + "rename/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        return new RestTemplate().postForEntity(requestUrl, newTitleWeb, DealWeb.class, uriVariables).getBody();
    }

    public void deleteDeal(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        new RestTemplate().delete(requestUrl, uriVariables);
    }

    private String getDealUrl() {
        return discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/internal/deal/";
    }
    
}
