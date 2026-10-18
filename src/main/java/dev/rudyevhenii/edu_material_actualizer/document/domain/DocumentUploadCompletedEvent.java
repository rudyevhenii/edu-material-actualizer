package dev.rudyevhenii.edu_material_actualizer.document.domain;

import java.util.UUID;

public record DocumentUploadCompletedEvent(UUID jobId, String savedFilePath) {
}
