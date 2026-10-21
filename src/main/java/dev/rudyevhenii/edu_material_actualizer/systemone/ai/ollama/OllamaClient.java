package dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama;

import dev.rudyevhenii.edu_material_actualizer.systemone.AbstractSystemOneClient;
import dev.rudyevhenii.edu_material_actualizer.systemone.SystemOneType;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama.properties.OllamaProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.validator.SystemOneThresholdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;

@Component
@ConditionalOnProperty(name = "app.system-one.type", havingValue = "ollama")
public class OllamaClient extends AbstractSystemOneClient {

    private final OllamaProperties ollamaProperties;

    public OllamaClient(ExecutorService virtualExecutor, RestClient ollamaRestClient,
                        SystemOneThresholdValidator thresholdValidator, OllamaProperties ollamaProperties) {
        super(virtualExecutor, ollamaRestClient, thresholdValidator);
        this.ollamaProperties = ollamaProperties;
    }

    @Override
    protected String getEndpoint() {
        return ollamaProperties.endpoint();
    }

    @Override
    protected String getModel() {
        return ollamaProperties.model().getModel();
    }

    @Override
    public SystemOneType getSystemOneType() {
        return SystemOneType.OLLAMA;
    }
}
