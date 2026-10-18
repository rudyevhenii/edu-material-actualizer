package dev.rudyevhenii.edu_material_actualizer.job.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.JobNotFoundException;
import dev.rudyevhenii.edu_material_actualizer.core.util.GeneratorUtils;
import dev.rudyevhenii.edu_material_actualizer.job.JobStatus;
import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;
import dev.rudyevhenii.edu_material_actualizer.job.storage.JobStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobStorage repository;
    private final GeneratorUtils generator;

    @Override
    public Job createJob(String fileName) {
        Job job = toDomain(fileName);
        log.info("Job created for document with filename: {}", fileName);
        return repository.createJob(job);
    }

    @Override
    public Job getJobById(UUID id) {
        return getById(id);
    }

    @Override
    public void updateJobStatus(UUID id, JobStatus jobStatus) {
        Job job = getById(id);
        job.setStatus(jobStatus);
        job.setUpdatedAt(generator.now());
        repository.updateJob(job);
    }

    private Job getById(UUID id) {
        return repository.findJobById(id.toString())
                .orElseThrow(() -> new JobNotFoundException("Job for document processing does not exist with jobId '%s'"
                        .formatted(id)));
    }

    private Job toDomain(String fileName) {
        return Job.builder()
                .id(generator.uuid())
                .fileName(fileName)
                .createdAt(generator.now())
                .updatedAt(generator.now())
                .build();
    }
}
