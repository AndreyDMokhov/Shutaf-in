package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.UserLanguageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by evgeny on 6/26/2017.
 */
@Service
@Transactional
public class UserLanguageServiceImpl implements UserLanguageService {


    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;


    @Override
    @SneakyThrows
    public void updateUserLanguage(AccountUserLanguageWeb userLanguageWeb, User user) {
        String url = discoveryRoutingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/users/%d/language", user.getId());
        new RestTemplate().put(url, userLanguageWeb, new HashMap<>());
    }

}
