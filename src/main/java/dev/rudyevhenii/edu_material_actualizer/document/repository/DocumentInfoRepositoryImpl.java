package dev.rudyevhenii.edu_material_actualizer.document.repository;

import dev.rudyevhenii.edu_material_actualizer.core.util.StreamUtils;
import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.entity.DocumentInfoEntity;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DocumentInfoRepositoryImpl implements DocumentInfoRepository {

    private final SpringDataDocumentInfoRepository repository;
    private final DocumentEntityMapper mapper;

    @Override
    public DocumentInfo create(DocumentInfo documentInfo) {
        DocumentInfoEntity entity = mapper.map(documentInfo);
        DocumentInfoEntity createdDocumentInfo = repository.save(entity);
        return mapper.map(createdDocumentInfo);
    }

    @Override
    public Optional<DocumentInfo> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::map);
    }

    @Override
    public List<DocumentInfo> findAll() {
        return StreamUtils.toStream(repository.findAll())
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<DocumentInfo> findAllByStatus(DocumentStatus documentStatus) {
        return StreamUtils.toStream(repository.findByDocumentStatus(documentStatus))
                .map(mapper::map)
                .toList();
    }

    @Override
    public void update(DocumentInfo documentInfo) {
        DocumentInfoEntity entity = mapper.map(documentInfo);
        repository.save(entity);
    }
}
