package dev.rudyevhenii.edu_material_actualizer.document.model;

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
public class DocumentInfoModel {
    private UUID id;
    private DocumentStatus documentStatus;
    private String fileName;
    private String contentType;
    private Instant createdAt;
    private Instant updatedAt;
}
