package com.shutafin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:server.properties")
public class ShutafInConfiguration {
}
