package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.DocumentInfoNotFoundException;
import dev.rudyevhenii.edu_material_actualizer.core.util.GeneratorUtils;
import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentInfoRequest;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentStatusResponse;
import dev.rudyevhenii.edu_material_actualizer.document.repository.DocumentInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentInfoServiceImpl implements DocumentInfoService {

    private final DocumentInfoRepository repository;
    private final GeneratorUtils generator;

    @Override
    public DocumentInfo createDocumentInfo(DocumentInfoRequest documentInfoRequest) {
        DocumentInfo documentInfo = toDomain(documentInfoRequest);
        log.info("Document info created with filename: {}", documentInfo.getFileName());
        return repository.create(documentInfo);
    }

    @Override
    public DocumentInfo getDocumentInfoById(UUID id) {
        return getById(id);
    }

    @Override
    public DocumentStatusResponse getDocumentInfoStatus(UUID id) {
        DocumentInfo documentInfo = getById(id);
        return toDocumentStatus(documentInfo);
    }

    @Override
    public List<DocumentInfo> getAllDocumentsInfo() {
        return repository.findAll();
    }

    @Override
    public List<DocumentInfo> getDocumentsInfoByStatus(DocumentStatus documentStatus) {
        return repository.findAllByStatus(documentStatus);
    }

    @Override
    public void updateDocumentInfoStatus(UUID id, DocumentStatus documentStatus) {
        DocumentInfo documentInfo = getById(id);
        documentInfo.setDocumentStatus(documentStatus);
        documentInfo.setUpdatedAt(generator.now());
        repository.update(documentInfo);
    }

    private DocumentInfo getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DocumentInfoNotFoundException("Document info does not exist with id '%s'"
                        .formatted(id)));
    }

    private DocumentInfo toDomain(DocumentInfoRequest documentInfoRequest) {
        return DocumentInfo.builder()
                .id(generator.uuid())
                .fileName(documentInfoRequest.fileName())
                .contentType(documentInfoRequest.contentType())
                .createdAt(generator.now())
                .updatedAt(generator.now())
                .build();
    }

    private DocumentStatusResponse toDocumentStatus(DocumentInfo documentInfo) {
        return DocumentStatusResponse.builder()
                .status(documentInfo.getDocumentStatus())
                .build();
    }
}
