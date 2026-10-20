package dev.rudyevhenii.edu_material_actualizer.document.listener;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusChangedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentUploadCompletedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentParserService;
import dev.rudyevhenii.edu_material_actualizer.minio.service.MinioStorageService;
import dev.rudyevhenii.edu_material_actualizer.systemone.SystemOneClient;
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
    private final SystemOneClient systemOneClient;

    @Async("virtualExecutor")
    @EventListener
    public void handleDocument(DocumentUploadCompletedEvent event) {
        log.info("Processing document with id [{}]", event.id());
        publisher.publishEvent(new DocumentStatusChangedEvent(event.id(), DocumentStatus.PROCESSING));

        InputStream inputStream = minioStorageService.downloadFile(event.savedFilePath());
        Document document = documentParserService.parseDocument(inputStream);

        systemOneClient.doActualizationCheck(document);
    }
}
