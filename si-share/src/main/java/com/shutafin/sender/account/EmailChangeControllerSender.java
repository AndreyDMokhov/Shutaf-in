package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountEmailChangeWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailChangeControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public void emailChange(AccountEmailChangeWeb emailChangeWeb, Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/change-email", userId);

        new RestTemplate().postForEntity(url, emailChangeWeb, Void.class);
    }
}
