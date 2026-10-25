package dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya;

import dev.rudyevhenii.edu_material_actualizer.systemone.AbstractSystemOneClient;
import dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya.properties.LayaProperties;
import dev.rudyevhenii.edu_material_actualizer.systemone.validator.SystemOneThresholdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;

@Component
@ConditionalOnProperty(name = "app.system-one.type", havingValue = "laya")
public class LayaClient extends AbstractSystemOneClient {

    private final LayaProperties layaProperties;

    public LayaClient(ExecutorService systemOneExecutor, RestClient layaRestClient,
                      SystemOneThresholdValidator thresholdValidator, LayaProperties layaProperties) {
        super(systemOneExecutor, layaRestClient, thresholdValidator);
        this.layaProperties = layaProperties;
    }

    @Override
    protected String getEndpoint() {
        return layaProperties.endpoint();
    }

    @Override
    protected String getModel() {
        return layaProperties.model().getModel();
    }
}
