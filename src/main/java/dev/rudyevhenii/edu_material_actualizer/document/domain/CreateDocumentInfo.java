package dev.rudyevhenii.edu_material_actualizer.document.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentInfo {
    private String fileName;
    private String contentType;
}
