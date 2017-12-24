package com.shutafin.sender.email;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailNotificationSenderControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {
        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/send";
        new RestTemplate().postForEntity(url, emailNotificationWeb, Void.class);
    }

    public Object confirmLink(String link, EmailReason emailReason) {
        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/confirm?link=" + link + "&reason=" + emailReason;
        return new RestTemplate().getForEntity(url, emailReason.getResponseObject()).getBody();
    }
}
