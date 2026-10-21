package dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama.properties;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama.OllamaModels;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.net.URI;

@ConfigurationProperties(prefix = "app.system-one.ollama")
public record OllamaProperties(
        @DefaultValue("http://localhost:11434") URI baseUrl,
        @DefaultValue("/v1/systemone") String endpoint,
        OllamaModels model
) {


}
