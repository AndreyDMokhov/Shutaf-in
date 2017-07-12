package com.shutafin.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface EnvironmentConfigurationService {

    String getServerAddress() throws IOException;
}
