package dev.rudyevhenii.edu_material_actualizer.document.mapper;

import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentInfoDto;
import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusDto;
import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusResponseDto;
import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {

    DocumentInfoDto map(DocumentInfo documentInfo);

    DocumentStatusResponseDto map(DocumentStatusResponse documentStatus);

    DocumentStatus map(DocumentStatusDto documentStatusDto);

    default OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }
}
