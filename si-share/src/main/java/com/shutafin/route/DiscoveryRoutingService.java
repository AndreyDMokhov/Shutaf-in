package com.shutafin.route;

import java.util.Map;

public interface DiscoveryRoutingService {

    String getRoute(RouteDirection direction);
    String getRoute(RouteDirection direction, String prefix);
    Map<RouteDirection, String> getServiceRoutes();
}
