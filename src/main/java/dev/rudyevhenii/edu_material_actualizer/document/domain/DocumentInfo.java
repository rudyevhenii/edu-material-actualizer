package dev.rudyevhenii.edu_material_actualizer.document.domain;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentInfo {
    private UUID id;
    @Builder.Default
    private DocumentStatus documentStatus = DocumentStatus.PENDING;
    private String fileName;
    private String contentType;
    private Instant createdAt;
    private Instant updatedAt;
}
