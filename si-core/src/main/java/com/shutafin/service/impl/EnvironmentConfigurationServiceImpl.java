package com.shutafin.service.impl;

import com.shutafin.service.EnvironmentConfigurationService;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentConfigurationServiceImpl implements EnvironmentConfigurationService {

    @Value("${windows.base.path}")
    private String windowsBasePath;

    @Value("${unix.base.path}")
    private String unixBasePath;

    @Override
    public String getLocalImagePath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return windowsBasePath;
        } else {
            return unixBasePath;
        }
    }
}
