package dev.rudyevhenii.edu_material_actualizer.document.dto;

import lombok.Builder;

@Builder
public record DocumentInfoRequest(
        String fileName,
        String contentType
) {
}
