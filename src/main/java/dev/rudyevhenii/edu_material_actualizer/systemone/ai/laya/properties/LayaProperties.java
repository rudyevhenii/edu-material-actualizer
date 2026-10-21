package dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.LayaModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.net.URI;

@ConfigurationProperties(prefix = "app.system-one.laya")
public record LayaProperties(
        URI baseUrl,
        @DefaultValue("/v1/systemone") String endpoint,
        @DefaultValue("laya_multilingual") LayaModel model
) {
}
