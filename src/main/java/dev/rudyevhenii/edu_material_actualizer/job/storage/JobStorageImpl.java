package dev.rudyevhenii.edu_material_actualizer.job.storage;

import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;
import dev.rudyevhenii.edu_material_actualizer.job.mapper.JobModelMapper;
import dev.rudyevhenii.edu_material_actualizer.job.model.JobModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JobStorageImpl implements JobStorage {

    private static final String JOB_KEY_PREFIX = "jobs:";
    private static final Duration JOB_TTL = Duration.ofMinutes(60);

    private final RedisTemplate<String, JobModel> redisTemplate;
    private final JobModelMapper mapper;

    @Override
    public Job createJob(Job job) {
        JobModel jobModel = mapper.map(job);
        redisTemplate.opsForValue().set(JOB_KEY_PREFIX + jobModel.getId(), jobModel, JOB_TTL);
        return mapper.map(jobModel);
    }

    @Override
    public Optional<Job> findJobById(UUID id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(id))
                .map(mapper::map);
    }

    @Override
    public void updateJob(Job job) {
        JobModel jobModel = mapper.map(job);
        redisTemplate.opsForValue().setIfPresent(JOB_KEY_PREFIX + jobModel.getId(), jobModel, JOB_TTL);
    }
}
