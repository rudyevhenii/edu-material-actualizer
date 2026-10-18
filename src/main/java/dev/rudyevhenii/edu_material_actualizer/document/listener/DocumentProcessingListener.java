package dev.rudyevhenii.edu_material_actualizer.document.listener;

import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentUploadCompletedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentParserService;
import dev.rudyevhenii.edu_material_actualizer.job.JobStatus;
import dev.rudyevhenii.edu_material_actualizer.job.domain.JobStatusChangedEvent;
import dev.rudyevhenii.edu_material_actualizer.minio.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentProcessingListener {

    private final DocumentParserService documentParserService;
    private final MinioStorageService minioStorageService;
    private final ApplicationEventPublisher publisher;

    @Async("virtualExecutor")
    @EventListener
    public void handleDocument(DocumentUploadCompletedEvent event) {
        log.info("Processing document with jobId [{}] and filename: {}", event.jobId(), event.savedFilePath());
        publisher.publishEvent(new JobStatusChangedEvent(event.jobId(), JobStatus.PROCESSING));

        InputStream inputStream = minioStorageService.downloadFile(event.savedFilePath());
        Document document = documentParserService.parseDocument(inputStream);
    }
}
