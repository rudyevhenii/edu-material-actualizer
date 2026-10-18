package dev.rudyevhenii.edu_material_actualizer.job.listener;

import dev.rudyevhenii.edu_material_actualizer.job.domain.JobStatusChangedEvent;
import dev.rudyevhenii.edu_material_actualizer.job.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobTrackerListener {

    private final JobService jobService;

    @EventListener
    public void onJobStatusChangedEvent(JobStatusChangedEvent event) {
        log.info("Job with id [{}] transitioned to [{}] status", event.id(), event.jobStatus());
        jobService.updateJobStatus(event.id(), event.jobStatus());
    }
}
