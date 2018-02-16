package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResetPasswordControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    public void resetPasswordRequest(AccountEmailRequest accountEmailRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/users/reset-password/request";
        restTemplate.postForEntity(url, accountEmailRequest, EmailNotificationWeb.class).getBody();

    }

    public void resetPassword(AccountResetPassword accountResetPassword) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/users/reset-password/confirmation";
        restTemplate.put(url, accountResetPassword);
    }
}
