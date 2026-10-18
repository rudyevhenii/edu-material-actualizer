package dev.rudyevhenii.edu_material_actualizer.minio.config;

import dev.rudyevhenii.edu_material_actualizer.minio.properties.MinioProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = MinioProperties.class)
public class MinioPropertiesConfig {
}
