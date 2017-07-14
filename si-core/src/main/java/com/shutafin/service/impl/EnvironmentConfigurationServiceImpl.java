package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.EnvironmentConfigurationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by evgeny on 7/10/2017.
 */
@Service
public class EnvironmentConfigurationServiceImpl implements EnvironmentConfigurationService {
    @Value("${external.port}")
    private String port;

    @Override
    public String getServerAddress() {
        String serverAddress = "";
        if ( ! port.isEmpty()){
            port = ":" + port;
        }
        try {
            InetAddress ip = InetAddress.getLocalHost();
            serverAddress = ip.getHostAddress();
        } catch (UnknownHostException e) {
            throw new ResourceNotFoundException();
        }
        return "http://" + serverAddress + port;
    }
}
