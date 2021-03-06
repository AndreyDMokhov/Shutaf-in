package com.shutafin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmailNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailNotificationApplication.class, args);
    }
}
