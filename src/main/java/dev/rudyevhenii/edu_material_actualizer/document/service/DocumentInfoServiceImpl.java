package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.DocumentInfoNotFoundException;
import dev.rudyevhenii.edu_material_actualizer.core.util.GeneratorUtils;
import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.CreateDocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.storage.DocumentInfoStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentInfoServiceImpl implements DocumentInfoService {

    private final DocumentInfoStorage repository;
    private final GeneratorUtils generator;

    @Override
    public DocumentInfo createDocumentInfo(CreateDocumentInfo createDocumentInfo) {
        DocumentInfo documentInfo = toDomain(createDocumentInfo);
        log.info("Document info created with filename: {}", documentInfo.getFileName());
        return repository.createDocumentInfo(documentInfo);
    }

    @Override
    public DocumentInfo getDocumentInfoById(UUID id) {
        return getById(id);
    }

    @Override
    public void updateDocumentInfoStatus(UUID id, DocumentStatus documentStatus) {
        DocumentInfo documentInfo = getById(id);
        documentInfo.setDocumentStatus(documentStatus);
        documentInfo.setUpdatedAt(generator.now());
        repository.updateDocumentInfo(documentInfo);
    }

    private DocumentInfo getById(UUID id) {
        return repository.findDocumentInfoById(id.toString())
                .orElseThrow(() -> new DocumentInfoNotFoundException("Document info does not exist with id '%s'"
                        .formatted(id)));
    }

    private DocumentInfo toDomain(CreateDocumentInfo createDocumentInfo) {
        return DocumentInfo.builder()
                .id(generator.uuid())
                .fileName(createDocumentInfo.getFileName())
                .contentType(createDocumentInfo.getContentType())
                .createdAt(generator.now())
                .updatedAt(generator.now())
                .build();
    }
}
