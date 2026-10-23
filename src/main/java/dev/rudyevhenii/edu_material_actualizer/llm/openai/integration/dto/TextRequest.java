package dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto;

import java.util.UUID;

public record TextRequest(
        UUID id,
        String originalText
) {
}
