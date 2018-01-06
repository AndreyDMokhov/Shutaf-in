package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoginControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    public AccountUserWeb login(AccountLoginRequest loginWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/users/login";
        ResponseEntity<AccountUserWeb> accountUserWebResponseEntity = restTemplate.postForEntity(url, loginWeb, AccountUserWeb.class);
        return accountUserWebResponseEntity.getBody();
    }

    public void checkUserPassword(AccountLoginRequest loginWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "users/check/user/password";
        restTemplate.postForEntity(url, loginWeb, AccountUserWeb.class);
    }
}
