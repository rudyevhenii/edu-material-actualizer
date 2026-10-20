package dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;

import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
        @JsonSubTypes.Type(value = Question.Noul.class, name = "noul")
)
public interface Question {

    @Builder
    record Noul(Object instructions,
                @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
                Map<String, ?> criteria) implements Question {
    }
}
