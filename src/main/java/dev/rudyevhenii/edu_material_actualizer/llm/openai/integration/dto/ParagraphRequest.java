package dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto;

import java.util.List;
import java.util.UUID;

public record ParagraphRequest(
        UUID id,
        List<TextRequest> textRequests
) {
}
