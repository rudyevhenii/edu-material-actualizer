package dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record Request(
        Object state,
        Map<String, ? extends Question> questions,
        String model
) {

    public Request(Object state, Map<String, ? extends Question> questions) {
        this(state, questions, null);
    }
}
