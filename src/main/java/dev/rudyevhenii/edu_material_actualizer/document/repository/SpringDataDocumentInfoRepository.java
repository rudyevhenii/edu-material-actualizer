package dev.rudyevhenii.edu_material_actualizer.document.repository;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.entity.DocumentInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataDocumentInfoRepository extends CrudRepository<DocumentInfoEntity, UUID> {

    List<DocumentInfoEntity> findByDocumentStatus(DocumentStatus documentStatus);
}
