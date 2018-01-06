package com.shutafin.sender.account;

import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserImageControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;


    public AccountUserImageWeb getCompressedUserImageByUserId(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/images", userId);

        return restTemplate.getForEntity(url, AccountUserImageWeb.class).getBody();
    }

    public AccountUserImageWeb getUserImageByUserId(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/images", userId);
        return restTemplate.getForEntity(url, AccountUserImageWeb.class).getBody();
    }

    public AccountUserImageWeb getOriginalUserImageByUserId(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/images/original", userId);
        return restTemplate.getForEntity(url, AccountUserImageWeb.class).getBody();
    }

    public AccountUserImageWeb addUserImage(Long userId, AccountUserImageWeb image) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/images", userId);
        return restTemplate.postForEntity(url, image, AccountUserImageWeb.class).getBody();
    }

    public void deleteUserImage(Long userId, Long userImageId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/images/%d", userId, userImageId);
        restTemplate.delete(url);
    }
}
