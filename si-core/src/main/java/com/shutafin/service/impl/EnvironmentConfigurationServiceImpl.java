package com.shutafin.service.impl;

import com.shutafin.service.EnvironmentConfigurationService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@Service
public class EnvironmentConfigurationServiceImpl implements EnvironmentConfigurationService {

    private static final String APPLICATION_PROPERTIES = "si-core/src/main/resources/application.properties";
    private static final String EXTERNAL_PORT = "external.port";

    @Override
    public String getServerAddress() {
        Properties prop = getProperties();
        String externalPort = prop.getProperty(EXTERNAL_PORT);
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error occurred");
        }
        String ip = addr.getHostAddress();
        return ip + ":" + externalPort;
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        FileInputStream input;
        try {
            input = new FileInputStream(APPLICATION_PROPERTIES);
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error occurred");
        }
        return prop;
    }

}
