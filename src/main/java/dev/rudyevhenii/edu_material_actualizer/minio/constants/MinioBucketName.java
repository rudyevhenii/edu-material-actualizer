package dev.rudyevhenii.edu_material_actualizer.minio.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MinioBucketName {
    TEMPORAL("temporal"),
    RESULTS("results");

    private final String name;
}
