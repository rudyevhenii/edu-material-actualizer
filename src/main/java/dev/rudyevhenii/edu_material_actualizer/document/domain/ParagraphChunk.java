package dev.rudyevhenii.edu_material_actualizer.document.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParagraphChunk {
    private UUID id;
    private List<TextChunk> textChunks;
    private XWPFParagraph paragraph;
}
