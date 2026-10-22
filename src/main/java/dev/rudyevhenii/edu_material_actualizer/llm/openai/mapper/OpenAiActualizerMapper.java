package dev.rudyevhenii.edu_material_actualizer.llm.openai.mapper;

import dev.rudyevhenii.edu_material_actualizer.document.domain.ParagraphChunk;
import dev.rudyevhenii.edu_material_actualizer.document.domain.TextChunk;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto.ParagraphRequest;
import dev.rudyevhenii.edu_material_actualizer.llm.openai.integration.dto.TextRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OpenAiActualizerMapper {

    @Mapping(target = "textRequests", source = "textChunks")
    ParagraphRequest map(ParagraphChunk paragraphChunk);

    TextRequest map(TextChunk textChunk);
}
