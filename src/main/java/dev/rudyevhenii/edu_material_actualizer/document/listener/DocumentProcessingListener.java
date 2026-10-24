package dev.rudyevhenii.edu_material_actualizer.document.listener;

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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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

        try {
            InputStream inputStream = minioStorageService.downloadTemporalFile(event.getSavedFilePath());
            Document document = documentParserService.parseDocument(inputStream);
            systemOneClient.doActualizationCheck(document);
            List<AggregatedBlock> blocksToUpdate = document.getAggregatedBlocks().stream()
                    .filter(AggregatedBlock::isRequiredActualization)
                    .toList();

            llmActualizerService.actualizeMaterials(blocksToUpdate);
            documentModifierService.updateTextBlocks(document);

            uploadProcessedDocument(event, document);
        } catch (IOException e) {
            log.error("Failed to process document with id [{}]. Error: {}", event.getId(), e.getMessage(), e);
            publisher.publishEvent(DocumentStatusChangedEvent.create(event.getId(), DocumentStatus.FAILED));
        }
    }

    private void uploadProcessedDocument(DocumentUploadCompletedEvent event, Document document) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             XWPFDocument poiDocument = document.getDocument()) {
            poiDocument.write(out);
            byte[] documentBytes = out.toByteArray();

            try (InputStream is = new ByteArrayInputStream(documentBytes)) {
                DocumentInfo documentInfo = documentInfoService.getDocumentInfoById(event.getId());
                minioStorageService.saveResultFile(documentInfo, is, documentBytes.length);
            }
            publisher.publishEvent(DocumentStatusChangedEvent.create(event.getId(), DocumentStatus.COMLETED));
        }
    }
}
