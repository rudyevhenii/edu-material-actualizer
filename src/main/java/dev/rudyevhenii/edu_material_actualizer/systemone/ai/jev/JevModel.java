package dev.rudyevhenii.edu_material_actualizer.systemone.ai.jev;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JevModel {
    JEV_1_13_0("jev-1.13.0"),
    JEV_LATEST("jev-latest"),

    JEV_1_14_0("jev-1.14.0"),
    JEV_PREVIEW("jev-preview");

    private final String model;
}
