package dev.rudyevhenii.edu_material_actualizer.job.model;

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
public class JobModel {
    private UUID id;
    private JobStatus status;
    private String fileName;
    private Instant createdAt;
    private Instant updatedAt;
}
