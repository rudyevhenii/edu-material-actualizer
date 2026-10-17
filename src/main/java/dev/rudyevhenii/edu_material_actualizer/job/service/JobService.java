package dev.rudyevhenii.edu_material_actualizer.job.service;

import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;

import java.util.UUID;

public interface JobService {

    Job createJob(String fileName);

    Job getJobById(UUID id);

    void updateJob(Job job);
}
