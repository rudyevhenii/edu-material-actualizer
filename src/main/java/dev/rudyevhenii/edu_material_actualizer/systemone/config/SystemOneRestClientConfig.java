package dev.rudyevhenii.edu_material_actualizer.systemone.config;

import dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties.LayaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SystemOneRestClientConfig {

    @Bean
    public RestClient layaRestClient(LayaProperties layaProperties) {
        return RestClient.builder()
                .baseUrl(layaProperties.baseUrl())
                .build();
    }
}
