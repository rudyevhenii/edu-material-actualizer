package dev.rudyevhenii.edu_material_actualizer.job.storage;

import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;

import java.util.Optional;
import java.util.UUID;

public interface JobStorage {

    Job createJob(Job job);

    Optional<Job> findJobById(UUID id);

    void updateJob(Job job);
}
