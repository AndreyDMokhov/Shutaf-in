package com.shutafin.configuration;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shutafin")
public class TestConfiguration {

    @Bean
    public DiscoveryClient discoveryClient() {
        return new SimpleDiscoveryClient(new SimpleDiscoveryProperties());
    }
}
