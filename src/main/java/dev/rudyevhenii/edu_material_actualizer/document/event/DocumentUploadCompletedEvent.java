package dev.rudyevhenii.edu_material_actualizer.document.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DocumentUploadCompletedEvent {
    private UUID id;
    private String savedFilePath;

    public static DocumentUploadCompletedEvent create(UUID id, String savedFilePath) {
        return DocumentUploadCompletedEvent.builder()
                .id(id)
                .savedFilePath(savedFilePath)
                .build();
    }
}
