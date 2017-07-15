package com.shutafin.service.impl;

import com.shutafin.service.EnvironmentConfigurationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@PropertySource("application.properties")
public class EnvironmentConfigurationServiceImpl implements EnvironmentConfigurationService {

    @Value("${external.port}")
    private String externalPort;

    @Override
    public String getServerAddress() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error occurred");
        }
        if (externalPort != null){
            ip = ip + ":" + externalPort;
        }
        return "http://" + ip;
    }

}
