package com.shutafin.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by evgeny on 2/7/2018.
 */
@Configuration
@EnableAsync
public class AsyncEmailSenderConfiguration implements AsyncConfigurer {

    private static final Integer MAX_POOL_SIZE = 10;
    private static final String THREAD_NAME_PREFIX = "AsyncSenderEmailExecutor-";

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
