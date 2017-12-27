package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoginControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public AccountUserWeb login(AccountLoginRequest loginWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/users/login";

        return new RestTemplate().postForEntity(url, loginWeb, AccountUserWeb.class).getBody();
    }
}
