package dev.rudyevhenii.edu_material_actualizer.systemone.config;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev.properties.JevProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties.LayaProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama.properties.OllamaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class SystemOneRestClientConfig {

    @Bean
    public RestClient layaRestClient(RestClient.Builder restClientBuilder, LayaProperties layaProperties) {
        return restClientBuilder
                .baseUrl(layaProperties.baseUrl())
                .build();
    }

    @Bean
    public RestClient ollamaRestClient(RestClient.Builder restClientBuilder, OllamaProperties ollamaProperties) {
        return restClientBuilder
                .baseUrl(ollamaProperties.baseUrl())
                .build();
    }

    @Bean
    public RestClient jevRestClient(RestClient.Builder restClientBuilder, JevProperties jevProperties) {
        return restClientBuilder
                .baseUrl(jevProperties.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jevProperties.apiKey())
                .build();
    }
}
