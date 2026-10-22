package dev.rudyevhenii.edu_material_actualizer.llm.openai.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;

import java.util.List;

public interface OpenAiActualizerService {

    void actualizeMaterials(List<AggregatedBlock> aggregatedBlocks);
}
