package com.shutafin.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscoveryRoutingServiceImpl implements DiscoveryRoutingService {

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public String getRoute(RouteDirection direction) {
        return getRoute(direction, null);
    }

    @Override
    public String getRoute(RouteDirection direction, String prefix) {


        List<ServiceInstance> instances = discoveryClient.getInstances(direction.getServiceId());
        if (instances == null || instances.isEmpty()) {
            return null;
        }

        return substituteIpWithDns(instances.get(0).getUri()) + (prefix == null ? "" : prefix);
    }

    @Override
    public String getExternalRoute(){
        List<ServiceInstance> instances = discoveryClient.getInstances(RouteDirection.SI_GATEWAY.getServiceId());
        if (instances == null || instances.isEmpty()) {
            return null;
        }

        return substituteIpWithDns(instances.get(0).getUri());
    }

    @Override
    public Map<RouteDirection, String> getServiceRoutes() {
        Map<RouteDirection, String> map = new HashMap<>();
        for (RouteDirection routeDirection : RouteDirection.getRouteDirections(discoveryClient.getServices())) {
            ServiceInstance instance = discoveryClient.getInstances(routeDirection.getServiceId()).get(0);

            if (instance == null) {
                continue;
            }

            map.put(routeDirection, substituteIpWithDns(instance.getUri()));
        }
        return map;
    }

    private String substituteIpWithDns(URI uri) {
        return uri.toString().replaceFirst("\\d+\\.\\d+\\.\\d+\\.\\d+", domainName);
    }
}
