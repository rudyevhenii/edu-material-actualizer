package dev.rudyevhenii.edu_material_actualizer.document.dto;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import lombok.Builder;

@Builder
public record DocumentStatusResponse(
        DocumentStatus status
) {
}
