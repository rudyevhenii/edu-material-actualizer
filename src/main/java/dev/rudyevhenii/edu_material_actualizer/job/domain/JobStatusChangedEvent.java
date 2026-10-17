package dev.rudyevhenii.edu_material_actualizer.job.domain;

import dev.rudyevhenii.edu_material_actualizer.job.JobStatus;

import java.util.UUID;

public record JobStatusChangedEvent(UUID id, JobStatus jobStatus) {
}
