package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.response.EmailRegistrationResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Override
    public void registerUser(AccountRegistrationRequest registrationRequestWeb) {

        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/users/registration/request";
        EmailNotificationWeb emailNotificationWeb = new RestTemplate().postForEntity(url, registrationRequestWeb, EmailNotificationWeb.class).getBody();

        url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/send";
        new RestTemplate().postForEntity(url, emailNotificationWeb, Void.class);
    }

    @Override
    public AccountUserWeb confirmRegistrationUser(String link) {

        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/confirm?link=" + link + "&reason=" + EmailReason.REGISTRATION;
        EmailRegistrationResponse emailRegistrationResponse = new RestTemplate().getForEntity(url, EmailRegistrationResponse.class).getBody();

        url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/registration/confirm/%d", emailRegistrationResponse.getUserId());

        return new RestTemplate().getForEntity(url, AccountUserWeb.class).getBody();
    }

}