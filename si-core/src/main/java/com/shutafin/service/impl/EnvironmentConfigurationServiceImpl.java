package com.shutafin.service.impl;

import com.shutafin.service.EnvironmentConfigurationService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Properties;

@Service
public class EnvironmentConfigurationServiceImpl implements EnvironmentConfigurationService {

    private static final String APPLICATION_PROPERTIES = "si-core/src/main/resources/application.properties";
    private static final String EXTERNAL_PORT = "external.port1";
    private static final String EXTERNAL_IP_SERVER = "external.ip.server1";

    @Override
    public String getServerAddress() throws IOException {
        Properties prop = getProperties();
        String externalPort = prop.getProperty(EXTERNAL_PORT);
        String externalIpServer = prop.getProperty(EXTERNAL_IP_SERVER);
        URL whatismyip = new URL(externalIpServer);
        BufferedReader input = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        return input.readLine() + ":" + externalPort;
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
