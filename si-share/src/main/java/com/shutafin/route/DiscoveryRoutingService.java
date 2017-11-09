package com.shutafin.route;

import java.util.Map;

public interface DiscoveryRoutingService {

    String getRoute(RouteDirection direction);
    Map<RouteDirection, String> getServiceRoutes();
}
