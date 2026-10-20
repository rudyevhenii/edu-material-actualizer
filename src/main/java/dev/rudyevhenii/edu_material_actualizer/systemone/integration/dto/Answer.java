package dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
        @JsonSubTypes.Type(value = Answer.Noul.class, name = "noul")
)
public interface Answer {

    record Noul(Double noul) implements Answer {
    }
}
