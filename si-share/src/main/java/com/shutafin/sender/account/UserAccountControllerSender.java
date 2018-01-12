package com.shutafin.sender.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.account.*;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserAccountControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    public AccountUserImageWeb updateUserAccountProfileImage(Long userId, AccountUserImageWeb userImageWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/profile-image", userId);
        return restTemplate.postForEntity(url, userImageWeb, AccountUserImageWeb.class).getBody();
    }

    public AccountUserImageWeb getUserAccountProfileImage(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/profile-image", userId);
        return restTemplate.getForEntity(url, AccountUserImageWeb.class).getBody();
    }

    public void deleteUserAccountProfileImage(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/profile-image", userId);
        restTemplate.delete(url);
    }


    public void updateUserLanguage(AccountUserLanguageWeb userLanguageWeb, Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/language", userId);
        restTemplate.put(url, userLanguageWeb);
    }

    public LanguageWeb getUserLanguage(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/language", userId);
        return restTemplate.getForEntity(url, LanguageWeb.class).getBody();
    }

    public AccountUserInfoResponseDTO getUserInfo(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/info", userId);
        return restTemplate.getForEntity(url, AccountUserInfoResponseDTO.class).getBody();
    }

    public void updateUserInfo(Long userId, AccountUserInfoRequest userInfoRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/info", userId);
        restTemplate.put(url, userInfoRequest);
    }

    public AccountUserWeb getBaseUserInfo(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/info-base", userId);
        return restTemplate.getForEntity(url, AccountUserWeb.class).getBody();
    }

    @SneakyThrows
    public List<AccountUserWeb> getBaseUserInfos(List<Long> userIds) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/users/info-base";
        String jsonBody = restTemplate.postForEntity(url, userIds, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<AccountUserWeb>>() {
        });
    }

    public UserSearchResponse getUserSearchObject(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/info-search/%d", userId);
        return restTemplate.getForEntity(url, UserSearchResponse.class).getBody();
    }

    public void updateUserAccountStatus(Long userId, Integer statusId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/update-account-status", userId);

        restTemplate.put(url, statusId);
    }

    public Integer getUserAccountStatus(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/%d/get-account-status", userId);

        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

}
