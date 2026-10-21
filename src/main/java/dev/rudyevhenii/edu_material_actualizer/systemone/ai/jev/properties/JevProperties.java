package dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev.properties;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev.JevModel;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@Validated
@ConfigurationProperties(prefix = "app.system-one.jev")
public record JevProperties(
        @DefaultValue("https://api.typesafe.ai") URI baseUrl,
        @DefaultValue("/v1/systemone") String endpoint,
        @NotBlank String apiKey,
        @DefaultValue("jev_latest") JevModel model
) {
}
