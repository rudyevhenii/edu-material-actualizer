package dev.rudyevhenii.edu_material_actualizer.document.entity;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.UUID;

@Data
@RedisHash(value = DocumentInfoEntity.KEY_PREFIX, timeToLive = 604800)
public class DocumentInfoEntity {
    public static final String KEY_PREFIX = "documentInfo";

    @Id
    private UUID id;
    @Indexed
    private DocumentStatus documentStatus;
    private String fileName;
    private String contentType;
    private Instant createdAt;
    private Instant updatedAt;
}
