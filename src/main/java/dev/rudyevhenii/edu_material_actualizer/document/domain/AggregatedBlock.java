package dev.rudyevhenii.edu_material_actualizer.document.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedBlock {
    private UUID id;
    private String aggregatedText;
    private boolean requiredActualization;
    private List<ParagraphChunk> paragraphChunks;
}
