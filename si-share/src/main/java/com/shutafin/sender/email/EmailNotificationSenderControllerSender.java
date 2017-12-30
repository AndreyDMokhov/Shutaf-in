package com.shutafin.sender.email;

import com.shutafin.model.exception.APIExceptionClient;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailNotificationSenderControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/send";
            new RestTemplate().postForEntity(url, emailNotificationWeb, Void.class);
        } catch (HttpClientErrorException e) {
            APIExceptionClient.getException(e);
        }
    }

    public Object confirmLink(String link, EmailReason emailReason) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) + "/email/confirm?link=" + link + "&reason=" + emailReason;
            return new RestTemplate().getForEntity(url, emailReason.getResponseObject()).getBody();
        } catch (HttpClientErrorException e) {
            APIExceptionClient.getException(e);
            return null;
        }
    }

    public Boolean isLinkValid(String link, EmailReason emailReason) {
        String url = routingService.getRoute(RouteDirection.SI_EMAIL_NOTIFICATION) +
                String.format("/email/validate/%s?reason=%s", link, emailReason);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Boolean> entity = new HttpEntity<Boolean>(null, headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, entity, Boolean.class).getBody();
    }
}
