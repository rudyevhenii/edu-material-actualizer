package dev.rudyevhenii.edu_material_actualizer.document.event;

import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
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
public class DocumentStatusChangedEvent {
    private UUID id;
    private DocumentStatus documentStatus;

    public static DocumentStatusChangedEvent create(UUID id, DocumentStatus documentStatus) {
        return DocumentStatusChangedEvent.builder()
                .id(id)
                .documentStatus(documentStatus)
                .build();
    }
}
