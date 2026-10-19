package dev.rudyevhenii.edu_material_actualizer.document.domain;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;

import java.util.UUID;

public record DocumentStatusChangedEvent(UUID id, DocumentStatus documentStatus) {
}
