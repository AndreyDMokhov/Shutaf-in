package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RegistrationControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    public void registerUser(AccountRegistrationRequest registrationRequestWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/users/registration/request";
        restTemplate.postForEntity(url, registrationRequestWeb, EmailNotificationWeb.class).getBody();
    }

    public void confirmRegistration(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/registration/confirm/%d", userId);
        restTemplate.getForEntity(url, Void.class);
    }
}


