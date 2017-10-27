package com.shutafin.service;

import com.shutafin.model.RouteDirection;

import java.util.Map;

public interface DiscoveryRoutingService {

    String getRoute(RouteDirection direction);
    Map<RouteDirection, String> getServiceRoutes();
}
