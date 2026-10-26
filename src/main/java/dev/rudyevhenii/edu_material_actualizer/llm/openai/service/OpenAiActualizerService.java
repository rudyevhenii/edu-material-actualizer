package dev.rudyevhenii.edu_material_actualizer.llm.openai.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;

public interface OpenAiActualizerService {

    void actualizeMaterials(Document document);
}
