package dev.rudyevhenii.edu_material_actualizer.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.executor.pool-size")
public record ExecutorProperties(
        int systemOne,
        int llm
) {
}
