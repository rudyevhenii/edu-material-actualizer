package dev.rudyevhenii.edu_material_actualizer.systemone.config;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev.properties.JevProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties.LayaProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama.properties.OllamaProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.properties.SystemOneProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {
        LayaProperties.class,
        OllamaProperties.class,
        SystemOneProperties.class,
        JevProperties.class
})
public class SystemOnePropertiesConfig {
}
