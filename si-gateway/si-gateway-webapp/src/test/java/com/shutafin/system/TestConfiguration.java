package com.shutafin.system;

import com.shutafin.service.ChangePasswordService;
import com.shutafin.service.impl.ChangePasswordServiceImpl;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public DiscoveryClient discoveryClient() {
        return new SimpleDiscoveryClient(new SimpleDiscoveryProperties());
    }

    @Bean
    public ChangePasswordService changePasswordService() {
        return new ChangePasswordServiceImpl();
    }
}
