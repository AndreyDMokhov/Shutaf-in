package com.shutafin.sender.email;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailNotificationSenderControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;
    
    @Autowired
    private RestTemplate restTemplate;

    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {
        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/send";
        restTemplate.postForEntity(url, emailNotificationWeb, Void.class);
    }

    public Object confirmLink(String link, EmailReason emailReason) {
        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/confirm?link=" + link + "&reason=" + emailReason;
        return restTemplate.getForEntity(url, emailReason.getResponseObject()).getBody();
    }

    public Boolean isLinkValid(String link, EmailReason emailReason) {
        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) +
                String.format("/email/validate/%s?reason=%s", link, emailReason);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Boolean> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class).getBody();
    }
}
