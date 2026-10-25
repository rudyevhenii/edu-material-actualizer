package dev.rudyevhenii.edu_material_actualizer.core.config;

import dev.rudyevhenii.edu_material_actualizer.core.properties.ExecutorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ExecutorServiceConfig {

    private final ExecutorProperties executorProperties;

    @Bean
    public ExecutorService virtualExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public ExecutorService systemOneExecutor() {
        return Executors.newFixedThreadPool(executorProperties.systemOne());
    }

    @Bean
    public ExecutorService llmExecutor() {
        return Executors.newFixedThreadPool(executorProperties.llm());
    }
}
