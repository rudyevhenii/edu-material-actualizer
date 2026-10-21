package dev.rudyevhenii.edu_material_actualizer.systemone.properties;

import dev.rudyevhenii.edu_material_actualizer.systemone.SystemOneType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.system-one")
public record SystemOneProperties(
        SystemOneType type
) {
}
