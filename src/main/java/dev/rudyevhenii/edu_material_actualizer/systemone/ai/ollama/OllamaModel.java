package dev.rudyevhenii.edu_material_actualizer.systemone.ai.ollama;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OllamaModel {
    TEV_1_LATEST("tev1:latest"),
    TEV_1_0_8B("tev1:0.8b"),
    TEV_1_4B("tev1:4b"),

    NIBMLE_LATEST("nimble:latest"),
    NIMBLE_9B("nimble:9b"),

    NIMBLE_V3_LATEST("nimble-v3:latest"),
    NIMBLE_V3_9B("nimble-v3:9b"),

    CLEF_FLASH_LATEST("clef-flash:latest"),
    CLEF_FLASH_9B("clef-flash:9b");

    private final String model;
}
