package com.shutafin;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@ComponentScan("com.shutafin")
@EnableAsync
class DummyUserApplication {

    private static final int COUNT_USERS = 1000;
    private static final int COUNT_THREADS = 100;
    private static final String THREAD_NAME_PREFIX = "DummyExecutor-";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.shutafin");
        context.register(DummyUserApplication.class);
        context.getBean(DummyUsersRunner.class).run(COUNT_USERS, COUNT_THREADS);
    }

    @Bean
    public DiscoveryClient discoveryClient() {
        return new SimpleDiscoveryClient(new SimpleDiscoveryProperties());
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(COUNT_THREADS);
        executor.setMaxPoolSize(COUNT_THREADS);
        executor.setQueueCapacity(COUNT_THREADS*2);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }

}
