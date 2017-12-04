package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.UserAccountService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {


    @Autowired
    private DiscoveryRoutingService routingService;


    @Override
    @Transactional
    public AccountUserImageWeb updateProfileImage(AccountUserImageWeb userImageWeb, User user) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/users/%d/profile-image", user.getId());

        ResponseEntity<AccountUserImageWeb> response = new RestTemplate().postForEntity(url, userImageWeb, AccountUserImageWeb.class);
        return response.getBody();
    }

    @Override
    public AccountUserImageWeb findUserAccountProfileImage(User user) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/users/%d/profile-image", user.getId());
        ResponseEntity<AccountUserImageWeb> response = new RestTemplate().getForEntity(url, AccountUserImageWeb.class);

        return response.getBody();
    }

    @Override
    @SneakyThrows
    public void deleteUserAccountProfileImage(User user) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/users/%d/profile-image", user.getId());
        new RestTemplate().delete(url);
    }

}
