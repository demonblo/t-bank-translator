package ru.tbank.translator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Value("${integration.multithreading.corePoolSize}")
    private int corePoolSize;
    @Value("${integration.multithreading.maxPoolSize}")
    private int maxPoolSize;
    @Value("${integration.multithreading.queueCapacity}")
    private int queueCapacity;

    @Override
    @Bean("asyncExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("custom-async-");
        executor.initialize();
        return executor;
    }
}
