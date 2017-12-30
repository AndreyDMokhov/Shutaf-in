package com.shutafin.sender.account;

import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserImageControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public AccountUserImageWeb getUserImage(Long userId, Long userImageId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/images/%d", userId, userImageId);
            return new RestTemplate().getForEntity(url, AccountUserImageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountUserImageWeb getUserImageByUserId(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/images", userId);
            return new RestTemplate().getForEntity(url, AccountUserImageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountUserImageWeb getOriginalUserImageByUserId(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/images/original", userId);
            return new RestTemplate().getForEntity(url, AccountUserImageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountUserImageWeb addUserImage(Long userId, AccountUserImageWeb image) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/images", userId);
            return new RestTemplate().postForEntity(url, image, AccountUserImageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void deleteUserImage(Long userId, Long userImageId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/images/%d", userId, userImageId);
            new RestTemplate().delete(url);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
