package dev.rudyevhenii.edu_material_actualizer.systemone;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.InstructionsReadException;
import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.systemone.constants.QuestionKey;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Question;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Request;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Response;
import dev.rudyevhenii.edu_material_actualizer.systemone.validator.SystemOneThresholdValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public abstract class AbstractSystemOneClient implements SystemOneClient {

    private final ExecutorService systemOneExecutor;
    private final RestClient restClient;
    private final SystemOneThresholdValidator thresholdValidator;

    @Value("classpath:string-templates/system-one/instructions.st")
    private Resource instructionsResource;

    public AbstractSystemOneClient(ExecutorService systemOneExecutor, RestClient restClient,
                                   SystemOneThresholdValidator thresholdValidator) {
        this.systemOneExecutor = systemOneExecutor;
        this.restClient = restClient;
        this.thresholdValidator = thresholdValidator;
    }

    protected abstract String getEndpoint();

    protected abstract String getModel();

    @Override
    public void doActualizationCheck(Document document) {
        List<CompletableFuture<Void>> futures = document.getAggregatedBlocks().stream()
                .map(block -> makeAsyncCall(block)
                        .thenAccept(response -> thresholdValidator.verifyThreshold(block, response)))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allOf.join();
    }

    protected CompletableFuture<Response> makeAsyncCall(AggregatedBlock aggregatedBlock) {
        try {
            String instructions = instructionsResource.getContentAsString(StandardCharsets.UTF_8);
            return CompletableFuture.supplyAsync(() -> restClient.post()
                    .uri(getEndpoint())
                    .body(Request.builder()
                            .state(aggregatedBlock.getAggregatedText())
                            .questions(Map.of(QuestionKey.REQUIRES_ACTUALIZATION.getValue(), Question.Noul.builder()
                                    .instructions(instructions)
                                    .build()))
                            .model(getModel())
                            .build())
                    .retrieve()
                    .body(Response.class), systemOneExecutor);
        } catch (IOException e) {
            throw new InstructionsReadException("Could not read instructions from file");
        }
    }
}
