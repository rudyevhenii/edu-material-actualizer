package dev.rudyevhenii.edu_material_actualizer.llm.openai.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.document.domain.ParagraphChunk;
import dev.rudyevhenii.edu_material_actualizer.document.domain.TextChunk;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto.ParagraphRequest;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto.ParagraphResponse;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto.TextResponse;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.mapper.OpenAiActualizerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAiActualizerServiceImpl implements OpenAiActualizerService {

    private static final ParameterizedTypeReference<List<ParagraphResponse>> PARAGRAPH_TYPE_REF =
            new ParameterizedTypeReference<>() {
    };

    private final ChatClient chatClient;
    private final ExecutorService virtualExecutor;
    private final OpenAiActualizerMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public void actualizeMaterials(List<AggregatedBlock> aggregatedBlocks) {
        List<CompletableFuture<Void>> futures = aggregatedBlocks.stream()
                .map(block -> fetchActualizedDocument(block)
                        .thenAccept(responses -> applyUpdatesToBlock(block, responses)))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();
    }

    private CompletableFuture<List<ParagraphResponse>> fetchActualizedDocument(AggregatedBlock aggregatedBlock) {
        String paragraph = convertToString(aggregatedBlock);

        return CompletableFuture.supplyAsync(() -> chatClient.prompt()
                .system(spec -> spec
                        .param("aggregatedText", aggregatedBlock.getAggregatedText()))
                .user(paragraph)
                .call()
                .entity(PARAGRAPH_TYPE_REF), virtualExecutor);
    }

    private void applyUpdatesToBlock(AggregatedBlock aggregatedBlock, List<ParagraphResponse> paragraphResponses) {
        Map<UUID, TextChunk> textChunksMap = aggregatedBlock.getParagraphChunks().stream()
                .map(ParagraphChunk::getTextChunks)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(TextChunk::getId, Function.identity()));

        for (ParagraphResponse paragraphResponse : paragraphResponses) {
            for (TextResponse textResponse : paragraphResponse.textResponses()) {
                TextChunk textChunk = textChunksMap.get(textResponse.id());
                textChunk.setUpdatedText(textResponse.updatedText());
            }
        }
    }

    private String convertToString(AggregatedBlock aggregatedBlock) {
        List<ParagraphRequest> list = aggregatedBlock.getParagraphChunks().stream()
                .map(mapper::map)
                .toList();
        return objectMapper.writeValueAsString(list);
    }
}
