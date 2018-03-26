package com.shutafin.sender.deal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.deal.*;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DealControllerSender {

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Autowired
    private RestTemplate restTemplate;

    public DealWeb initiateDeal(DealWeb dealWeb, Long userId) {
        String requestUrl = getDealUrl();
        InternalDealWeb internalDealWeb = new InternalDealWeb();
        internalDealWeb.setOriginUserId(userId);
        internalDealWeb.setTitle(dealWeb.getTitle());
        internalDealWeb.setUsers(dealWeb.getUsers());
        internalDealWeb = restTemplate.postForEntity(requestUrl, internalDealWeb, InternalDealWeb.class).getBody();
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
        restTemplate.put(requestUrl, request, uriVariables);
    }

    public void leaveDeal(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "leave/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        restTemplate.put(requestUrl, request, uriVariables);
    }

    public void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId) {
        String requestUrl = getDealUrl() + "remove";
        InternalDealUserWeb internalDealUserWeb = new InternalDealUserWeb(dealId, userOriginId,
                userToRemoveId);
        restTemplate.put(requestUrl, internalDealUserWeb);
    }

    public void addDealUser(Long dealId, Long userOriginId, Long userToAddId) {
        String requestUrl = getDealUrl() + "add";
        InternalDealUserWeb internalDealUserWeb = new InternalDealUserWeb(dealId, userOriginId, userToAddId);
        restTemplate.put(requestUrl, internalDealUserWeb);
    }

    public void confirmAddDealUser(Long dealId, Long userOriginId, Long userToAddId) {
        String requestUrl = getDealUrl() + "confirmation/add";
        InternalDealUserWeb internalDealUserWeb = new InternalDealUserWeb(dealId, userOriginId, userToAddId);
        restTemplate.put(requestUrl, internalDealUserWeb);
    }

    @SneakyThrows
    public List<DealUserWeb> getAllUserDeals(Long userId) {
        String requestUrl = getDealUrl() + "all/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);

        String body = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class, uriVariables).getBody();
        return new ObjectMapper().readValue(body, new TypeReference<List<DealUserWeb>>() {});
    }

    public DealResponse getDeal(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        return restTemplate.getForEntity(requestUrl, DealResponse.class, uriVariables).getBody();
    }

    public DealWeb renameDeal(Long dealId, Long userId, DealTitleChangeWeb dealTitleChangeWeb) {
        String requestUrl = getDealUrl() + "rename/{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        return restTemplate.postForEntity(requestUrl, dealTitleChangeWeb, DealWeb.class, uriVariables).getBody();
    }

    public void deleteDeal(Long dealId, Long userId) {
        String requestUrl = getDealUrl() + "{dealId}/{userId}";
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("dealId", dealId);
        uriVariables.put("userId", userId);
        restTemplate.delete(requestUrl, uriVariables);
    }

    private String getDealUrl() {
        return discoveryRoutingService.getRoute(RouteDirection.SI_DEAL) + "/deal/";
    }

}
