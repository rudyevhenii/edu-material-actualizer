package dev.rudyevhenii.edu_material_actualizer.document.mapper;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.model.DocumentInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentModelMapper {

    DocumentInfoModel map(DocumentInfo documentInfo);

    DocumentInfo map(DocumentInfoModel documentInfoModel);
}
