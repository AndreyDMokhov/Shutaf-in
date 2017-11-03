package com.shutafin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public enum RouteDirection {

    SI_GATEWAY("si-webapp"),
    SI_ADMIN("si-admin"),
    SI_DEAL("si-deal")
    ;

    private String serviceId;

    public static List<RouteDirection> getRouteDirections(List<String> serviceNames) {
        if (serviceNames == null || serviceNames.isEmpty()) {
            return new ArrayList<>();
        }

        List<RouteDirection> routeDirections = new ArrayList<>();
        for (RouteDirection routeDirection : values()) {
            if (serviceNames.contains(routeDirection.getServiceId())) {
                routeDirections.add(routeDirection);
                serviceNames.remove(routeDirection.getServiceId());
            }
        }

        return routeDirections;
    }

}
