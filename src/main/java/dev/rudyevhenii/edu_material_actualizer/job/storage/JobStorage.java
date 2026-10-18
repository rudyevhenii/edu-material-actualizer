package dev.rudyevhenii.edu_material_actualizer.job.storage;

import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;

import java.util.Optional;

public interface JobStorage {

    Job createJob(Job job);

    Optional<Job> findJobById(String id);

    void updateJob(Job job);
}
