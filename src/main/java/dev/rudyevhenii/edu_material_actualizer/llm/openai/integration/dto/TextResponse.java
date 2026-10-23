package dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto;

import java.util.UUID;

public record TextResponse(
        UUID id,
        String updatedText
) {
}
