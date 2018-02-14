package com.shutafin;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shutafin")
class DummyUserRunnerApplication {

    private static final int COUNT_USERS = 1000;
    private static final int COUNT_THREADS = 100;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.shutafin");
        context.register(DummyUserRunnerApplication.class);
        context.getBean(GenerateDummyUsersService.class).run(COUNT_USERS, COUNT_THREADS);

    }

    @Bean
    public DiscoveryClient discoveryClient() {
        return new SimpleDiscoveryClient(new SimpleDiscoveryProperties());
    }

}
