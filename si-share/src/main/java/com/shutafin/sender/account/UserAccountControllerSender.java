package com.shutafin.sender.account;

import com.shutafin.model.web.account.*;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Component
public class UserAccountControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public AccountUserImageWeb updateUserAccountProfileImage(Long userId, AccountUserImageWeb userImageWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/profile-image", userId);

        return new RestTemplate().postForEntity(url, userImageWeb, AccountUserImageWeb.class).getBody();
    }

    public AccountUserImageWeb getUserAccountProfileImage(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/profile-image", userId);

        return new RestTemplate().getForEntity(url, AccountUserImageWeb.class).getBody();
    }

    public void deleteUserAccountProfileImage(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/profile-image", userId);

        new RestTemplate().delete(url);
    }


    public void updateUserLanguage(AccountUserLanguageWeb userLanguageWeb, Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/language", userId);

        new RestTemplate().put(url, userLanguageWeb);
    }

    public LanguageWeb getUserLanguage(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/language", userId);

        return new RestTemplate().getForEntity(url, LanguageWeb.class).getBody();
    }

    public AccountUserInfoResponseDTO getUserInfo(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/info", userId);

        return new RestTemplate().getForEntity(url, AccountUserInfoResponseDTO.class).getBody();
    }

    public void updateUserInfo(Long userId, AccountUserInfoRequest userInfoRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/info", userId);

        new RestTemplate().put(url, userInfoRequest);
    }

    public AccountUserWeb getBaseUserInfo(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/info-base", userId);

        return new RestTemplate().getForEntity(url, AccountUserWeb.class).getBody();
    }

    public List<AccountUserWeb> getBaseUserInfos(List<Long> userIds) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/users/info-base";

        return new RestTemplate().getForEntity(
                url,
                List.class,
                new HashMap<String, Object>() {{
                    put("userIds", userIds);
                }}).getBody();
    }
}
