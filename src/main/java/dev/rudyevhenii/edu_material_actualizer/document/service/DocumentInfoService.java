package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentInfoRequest;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentStatusResponse;

import java.util.UUID;

public interface DocumentInfoService {

    DocumentInfo createDocumentInfo(DocumentInfoRequest documentInfoRequest);

    DocumentInfo getDocumentInfoById(UUID id);

    DocumentStatusResponse getDocumentStatus(UUID id);

    void updateDocumentInfoStatus(UUID id, DocumentStatus documentStatus);
}
