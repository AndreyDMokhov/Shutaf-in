package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailChangeControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    public EmailNotificationWeb validateChangeEmailRequest(AccountEmailChangeValidationRequest emailChangeWeb, Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/change-email", userId);
        return restTemplate.postForEntity(url, emailChangeWeb, EmailNotificationWeb.class).getBody();
    }

    public void changeEmail(Long userId, AccountEmailChangeRequest accountEmailChangeRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/change-email", userId);
        restTemplate.put(url, accountEmailChangeRequest);
    }
}
