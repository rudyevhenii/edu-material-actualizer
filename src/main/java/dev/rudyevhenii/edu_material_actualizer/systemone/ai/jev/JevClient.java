package dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev;

import dev.rudyevhenii.edu_material_actualizer.systemone.AbstractSystemOneClient;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev.properties.JevProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.validator.SystemOneThresholdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;

@Component
@ConditionalOnProperty(name = "app.system-one.type", havingValue = "jev")
public class JevClient extends AbstractSystemOneClient {

    private final JevProperties jevProperties;

    public JevClient(ExecutorService systemOneExecutor, RestClient jevRestClient,
                     SystemOneThresholdValidator thresholdValidator, JevProperties jevProperties) {
        super(systemOneExecutor, jevRestClient, thresholdValidator);
        this.jevProperties = jevProperties;
    }

    @Override
    protected String getEndpoint() {
        return jevProperties.endpoint();
    }

    @Override
    protected String getModel() {
        return jevProperties.model().getModel();
    }
}
