package dev.rudyevhenii.edu_material_actualizer.core.config;

import dev.rudyevhenii.edu_material_actualizer.core.properties.ExecutorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = ExecutorProperties.class)
public class PropertiesConfig {
}
