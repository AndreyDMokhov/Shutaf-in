package com.shutafin.service.impl;


import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private DiscoveryRoutingService routingService;


    public AccountUserWeb getUserByLoginWebModel(AccountLoginRequest loginWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/users/login";

        return new RestTemplate().postForEntity(url, loginWeb, AccountUserWeb.class).getBody();
    }




}
