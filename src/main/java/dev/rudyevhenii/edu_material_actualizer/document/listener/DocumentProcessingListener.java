package dev.rudyevhenii.edu_material_actualizer.document.listener;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.FileUploadException;
import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.event.DocumentStatusChangedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.event.DocumentUploadCompletedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentInfoService;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentModifierService;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentParserService;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.service.OpenAiActualizerService;
import dev.rudyevhenii.edu_material_actualizer.minio.service.MinioStorageService;
import dev.rudyevhenii.edu_material_actualizer.systemone.SystemOneClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentProcessingListener {

    private final DocumentParserService documentParserService;
    private final MinioStorageService minioStorageService;
    private final ApplicationEventPublisher publisher;
    private final SystemOneClient systemOneClient;
    private final OpenAiActualizerService llmActualizerService;
    private final DocumentModifierService documentModifierService;
    private final DocumentInfoService documentInfoService;

    @Async("virtualExecutor")
    @EventListener
    public void handleDocument(DocumentUploadCompletedEvent event) {
        log.info("Processing document with id [{}]", event.getId());
        publisher.publishEvent(DocumentStatusChangedEvent.create(event.getId(), DocumentStatus.PROCESSING));

        InputStream inputStream = minioStorageService.downloadFile(event.getSavedFilePath());
        Document document = documentParserService.parseDocument(inputStream);

        systemOneClient.doActualizationCheck(document);
        List<AggregatedBlock> blocksToUpdate = document.getAggregatedBlocks().stream()
                .filter(AggregatedBlock::isRequiredActualization)
                .toList();

        llmActualizerService.actualizeMaterials(blocksToUpdate);
        documentModifierService.updateTextBlocks(document);

        uploadProcessedDocument(event, document);
    }

    private void uploadProcessedDocument(DocumentUploadCompletedEvent event, Document document) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            document.getDocument().write(out);
            byte[] documentBytes = out.toByteArray();

            try (InputStream is = new ByteArrayInputStream(documentBytes)) {
                DocumentInfo documentInfo = documentInfoService.getDocumentInfoById(event.getId());
                minioStorageService.saveResultFile(documentInfo, is, documentBytes.length);
            }
            publisher.publishEvent(DocumentStatusChangedEvent.create(event.getId(), DocumentStatus.COMLETED));

        } catch (IOException e) {
            log.error("Failed to write document bytes for id [{}]", event.getId(), e);
            publisher.publishEvent(DocumentStatusChangedEvent.create(event.getId(), DocumentStatus.FAILED));
            throw new FileUploadException("Error while uploading actualized file");
        }
    }
}
