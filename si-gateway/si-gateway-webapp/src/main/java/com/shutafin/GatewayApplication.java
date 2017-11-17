package com.shutafin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableScheduling
@EnableEurekaClient
public class GatewayApplication {


    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class, args);
    }
}
