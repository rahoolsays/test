package com.simpaisa.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadConfig {
    @Bean(name="threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor(){
        return new ThreadPoolTaskExecutor();
    }
}
