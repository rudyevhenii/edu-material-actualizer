package dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "app.system-one.laya")
public record LayaProperties(
        URI baseUrl
) {
}
