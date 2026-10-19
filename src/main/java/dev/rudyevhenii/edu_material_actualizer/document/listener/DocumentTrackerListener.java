package dev.rudyevhenii.edu_material_actualizer.document.listener;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusChangedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentTrackerListener {

    private final DocumentInfoService documentInfoService;

    @EventListener
    public void onJobStatusChangedEvent(DocumentStatusChangedEvent event) {
        log.info("Document info with id [{}] transitioned to [{}] status", event.id(), event.documentStatus());
        documentInfoService.updateDocumentInfoStatus(event.id(), event.documentStatus());
    }
}
