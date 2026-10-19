package dev.rudyevhenii.edu_material_actualizer.document.domain;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;

import java.time.Instant;
import java.util.UUID;

public record DocumentStatusResponse(
        UUID id,
        DocumentStatus documentStatus,
        String fileName,
        Instant createdAt,
        Instant updatedAt
) {
}
