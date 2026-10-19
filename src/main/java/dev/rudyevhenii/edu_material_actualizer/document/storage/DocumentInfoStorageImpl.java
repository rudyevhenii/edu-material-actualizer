package dev.rudyevhenii.edu_material_actualizer.document.storage;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentModelMapper;
import dev.rudyevhenii.edu_material_actualizer.document.model.DocumentInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DocumentInfoStorageImpl implements DocumentInfoStorage {

    private static final String JOB_KEY_PREFIX = "documentsInfo:";
    private static final Duration JOB_TTL = Duration.ofMinutes(60);

    private final RedisTemplate<String, DocumentInfoModel> redisTemplate;
    private final DocumentModelMapper mapper;

    @Override
    public DocumentInfo createDocumentInfo(DocumentInfo documentInfo) {
        DocumentInfoModel documentInfoModel = mapper.map(documentInfo);
        redisTemplate.opsForValue().set(JOB_KEY_PREFIX + documentInfoModel.getId(), documentInfoModel, JOB_TTL);
        return mapper.map(documentInfoModel);
    }

    @Override
    public Optional<DocumentInfo> findDocumentInfoById(String id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(JOB_KEY_PREFIX + id))
                .map(mapper::map);
    }

    @Override
    public void updateDocumentInfo(DocumentInfo documentInfo) {
        DocumentInfoModel documentInfoModel = mapper.map(documentInfo);
        redisTemplate.opsForValue().setIfPresent(JOB_KEY_PREFIX + documentInfoModel.getId(), documentInfoModel, JOB_TTL);
    }
}
