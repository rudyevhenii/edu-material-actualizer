package dev.rudyevhenii.edu_material_actualizer.document.mapper;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.entity.DocumentInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentEntityMapper {

    DocumentInfoEntity map(DocumentInfo documentInfo);

    DocumentInfo map(DocumentInfoEntity documentInfoModel);
}
