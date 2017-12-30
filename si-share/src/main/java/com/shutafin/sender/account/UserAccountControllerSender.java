package com.shutafin.sender.account;

import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.*;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserAccountControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public AccountUserImageWeb updateUserAccountProfileImage(Long userId, AccountUserImageWeb userImageWeb) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/profile-image", userId);
            return new RestTemplate().postForEntity(url, userImageWeb, AccountUserImageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountUserImageWeb getUserAccountProfileImage(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/profile-image", userId);
            return new RestTemplate().getForEntity(url, AccountUserImageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void deleteUserAccountProfileImage(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/profile-image", userId);
            new RestTemplate().delete(url);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }


    public void updateUserLanguage(AccountUserLanguageWeb userLanguageWeb, Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/language", userId);
            new RestTemplate().put(url, userLanguageWeb);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public LanguageWeb getUserLanguage(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/language", userId);
            return new RestTemplate().getForEntity(url, LanguageWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountUserInfoResponseDTO getUserInfo(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/info", userId);
            return new RestTemplate().getForEntity(url, AccountUserInfoResponseDTO.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void updateUserInfo(Long userId, AccountUserInfoRequest userInfoRequest) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/info", userId);
            new RestTemplate().put(url, userInfoRequest);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AccountUserWeb getBaseUserInfo(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/%d/info-base", userId);
            return new RestTemplate().getForEntity(url, AccountUserWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public List<AccountUserWeb> getBaseUserInfos(List<Long> userIds) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    "/users/info-base";
            return new RestTemplate().postForEntity(url, userIds, List.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public UserSearchResponse getUserSearchObject(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/users/info-search/%d", userId);
            return new RestTemplate().getForEntity(url, UserSearchResponse.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
