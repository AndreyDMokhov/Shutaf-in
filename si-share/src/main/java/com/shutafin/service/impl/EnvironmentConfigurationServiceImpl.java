package com.shutafin.service.impl;

import com.shutafin.model.exception.exceptions.SystemException;
import com.shutafin.service.EnvironmentConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Service
@Slf4j
public class EnvironmentConfigurationServiceImpl implements EnvironmentConfigurationService {

    @Value("${external.port}")
    private String port;

    @Value("${windows.base.path}")
    private String windowsBasePath;

    @Value("${unix.base.path}")
    private String unixBasePath;

    @Override
    public String getServerAddress() {
        String serverAddress = "";

        try {
            InetAddress ip = InetAddress.getLocalHost();
            serverAddress = ip.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Host cannot be resolved:");
            log.error(e.getMessage(), e);
            throw new SystemException(e.getMessage() + ":\n" + Arrays.toString(e.getStackTrace()));
        }

        String suffix = port.isEmpty() ? "" : String.format(":%s", port);
        return "http://" + serverAddress + suffix;
    }

    @Override
    public String getLocalImagePath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return windowsBasePath;
        } else {
            return unixBasePath;
        }
    }
}
