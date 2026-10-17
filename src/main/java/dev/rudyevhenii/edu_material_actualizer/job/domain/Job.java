package dev.rudyevhenii.edu_material_actualizer.job.domain;

import dev.rudyevhenii.edu_material_actualizer.job.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private UUID id;
    @Builder.Default
    private JobStatus status = JobStatus.PENDING;
    private String fileName;
    private Instant createdAt;
    private Instant updatedAt;
}
