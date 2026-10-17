package dev.rudyevhenii.edu_material_actualizer.document.mapper;

import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusResponseDto;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {

    DocumentStatusResponseDto map(DocumentStatusResponse response);

    default OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }
}
