package com.shutafin.controller;

import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private DiscoveryRoutingService route;

    @RequestMapping("users")
    public List getUsers() {

        ResponseEntity<List> forEntity = new RestTemplate().getForEntity(getUrl(), List.class);
        return forEntity.getBody();
    }

    private String getUrl() {
        return route.getRoute(RouteDirection.SI_GATEWAY) + "/users/all";
    }


}
