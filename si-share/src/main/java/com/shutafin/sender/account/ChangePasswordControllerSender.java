package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountChangePasswordWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ChangePasswordControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    public void changePassword(AccountChangePasswordWeb changePasswordWeb, Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/change-password", userId);
        restTemplate.put(url, changePasswordWeb);
    }
}
