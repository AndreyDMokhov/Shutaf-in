package com.shutafin.sender.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserFilterControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;


    @SneakyThrows
    public List<UserSearchResponse> getFilteredUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/filter/%d", userId);

        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {
        });
    }

    @SneakyThrows
    public List<UserSearchResponse> saveUserFiltersAndGetUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/save/%d", userId);
        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {});
    }


}
