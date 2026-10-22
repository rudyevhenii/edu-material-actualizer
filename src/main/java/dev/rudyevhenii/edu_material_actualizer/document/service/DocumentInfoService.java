package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.CreateDocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;

import java.util.UUID;

public interface DocumentInfoService {

    DocumentInfo createDocumentInfo(CreateDocumentInfo createDocumentInfo);

    DocumentInfo getDocumentInfoById(UUID id);

    void updateDocumentInfoStatus(UUID id, DocumentStatus documentStatus);
}
