package dev.rudyevhenii.edu_material_actualizer.document.dto;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import lombok.Builder;
import org.springframework.core.io.Resource;

@Builder
public record DocumentResponse(
        Resource resource,
        DocumentInfo documentInfo
) {
}
