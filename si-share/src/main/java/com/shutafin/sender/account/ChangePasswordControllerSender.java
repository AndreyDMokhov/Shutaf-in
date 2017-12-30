package com.shutafin.sender.account;

import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.AccountChangePasswordWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ChangePasswordControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public void changePassword(AccountChangePasswordWeb changePasswordWeb, Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/change-password", userId);
            new RestTemplate().put(url, changePasswordWeb);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
