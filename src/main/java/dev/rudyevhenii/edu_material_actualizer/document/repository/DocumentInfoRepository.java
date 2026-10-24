package dev.rudyevhenii.edu_material_actualizer.document.repository;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentInfoRepository {

    DocumentInfo create(DocumentInfo documentInfo);

    Optional<DocumentInfo> findById(UUID id);

    List<DocumentInfo> findAll();

    List<DocumentInfo> findAllByStatus(DocumentStatus documentStatus);

    void update(DocumentInfo documentInfo);
}
