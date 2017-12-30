package com.shutafin.sender.account;

import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailChangeControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public EmailNotificationWeb validateChangeEmailRequest(AccountEmailChangeValidationRequest emailChangeWeb, Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/change-email", userId);
            return new RestTemplate().postForEntity(url, emailChangeWeb, EmailNotificationWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void changeEmail(Long userId, AccountEmailChangeRequest accountEmailChangeRequest) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/change-email", userId);
            new RestTemplate().put(url, accountEmailChangeRequest);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
