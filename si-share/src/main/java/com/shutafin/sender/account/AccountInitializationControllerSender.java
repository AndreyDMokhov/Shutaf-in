package com.shutafin.sender.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AccountInitializationControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public List<LanguageWeb> getLanguages() {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/initialization/languages";
        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<LanguageWeb>>() {});
    }

    public AccountInitializationResponse getInitializationResponse(Long userId) {
        String accountUrl = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/initialization/%d/all", userId);
        return restTemplate.getForEntity(accountUrl, AccountInitializationResponse.class).getBody();
    }

}
