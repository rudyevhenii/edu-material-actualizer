package dev.rudyevhenii.edu_material_actualizer.document.domain;

import dev.rudyevhenii.edu_material_actualizer.job.JobStatus;

import java.time.Instant;
import java.util.UUID;

public record DocumentStatusResponse(
        UUID jobId,
        JobStatus jobStatus,
        String fileName,
        Instant createdAt,
        Instant updatedAt
) {
}
