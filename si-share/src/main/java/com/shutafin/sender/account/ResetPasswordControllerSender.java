package com.shutafin.sender.account;

import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ResetPasswordControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public EmailNotificationWeb getResetPasswordEmailNotification(AccountEmailRequest accountEmailRequest) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    "/users/reset-password/request";
            return new RestTemplate().postForEntity(url, accountEmailRequest, EmailNotificationWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void resetPassword(AccountResetPassword accountResetPassword) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    "/users/reset-password/confirmation";
            new RestTemplate().put(url, accountResetPassword);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
