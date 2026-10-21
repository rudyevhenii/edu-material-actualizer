package dev.rudyevhenii.edu_material_actualizer.systemone.ai.laya;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LayaModel {
    LAYA("laya"),
    LAYA_MULTILINGUAL("laya-multilingual"),
    LAYA_TYPED_DECISIONS("laya-typed-decisions");

    private final String model;
}
