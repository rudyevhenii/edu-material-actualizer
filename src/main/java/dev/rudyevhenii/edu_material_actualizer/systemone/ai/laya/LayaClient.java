package dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.InstructionsReadException;
import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.systemone.SystemOneClient;
import dev.rudyevhenii.edu_material_actualizer.systemone.constants.QuestionKey;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Question;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Request;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Response;
import dev.rudyevhenii.edu_material_actualizer.systemone.validator.SystemOneThresholdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
public class LayaClient implements SystemOneClient {

    private static final URI LAYA_URI = URI.create("/v1/systemone");

    private final RestClient layaRestClient;
    private final ExecutorService virtualExecutor;
    private final SystemOneThresholdValidator thresholdValidator;

    @Value("classpath:string-templates/system-one/instructions.st")
    private Resource instructionsResource;

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

    private CompletableFuture<Response> makeAsyncCall(AggregatedBlock aggregatedBlock) {
        try {
            String instructions = instructionsResource.getContentAsString(StandardCharsets.UTF_8);
            return CompletableFuture.supplyAsync(() -> layaRestClient.post()
                    .uri(LAYA_URI)
                    .body(Request.builder()
                            .state(aggregatedBlock.getAggregatedText())
                            .questions(Map.of(QuestionKey.REQUIRES_ACTUALIZATION.getValue(), Question.Noul.builder()
                                    .instructions(instructions)
                                    .build()))
                            .build())
                    .retrieve()
                    .body(Response.class), virtualExecutor);
        } catch (IOException e) {
            throw new InstructionsReadException("Could not read instructions from file");
        }
    }
}
