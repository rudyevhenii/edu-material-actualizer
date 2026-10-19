package dev.rudyevhenii.edu_material_actualizer.document.storage;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;

import java.util.Optional;

public interface DocumentInfoStorage {

    DocumentInfo createDocumentInfo(DocumentInfo documentInfo);

    Optional<DocumentInfo> findDocumentInfoById(String id);

    void updateDocumentInfo(DocumentInfo job);
}
