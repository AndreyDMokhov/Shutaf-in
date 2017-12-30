package com.shutafin.sender.account;

import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AccountInitializationControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @SneakyThrows
    public List<LanguageWeb> getLanguages() {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/initialization/languages";
            ResponseEntity<List> languages = new RestTemplate().getForEntity(url, List.class);
            return languages.getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountInitializationResponse getInitializationResponse(Long userId) {
        try {
            String accountUrl = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/initialization/%d/all", userId);
            return new RestTemplate().getForEntity(accountUrl, AccountInitializationResponse.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

}
