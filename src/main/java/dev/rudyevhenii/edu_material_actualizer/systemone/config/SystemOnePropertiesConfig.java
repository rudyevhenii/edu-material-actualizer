package dev.rudyevhenii.edu_material_actualizer.systemone.config;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties.LayaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = LayaProperties.class)
public class SystemOnePropertiesConfig {
}
