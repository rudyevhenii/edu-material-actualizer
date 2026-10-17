package dev.rudyevhenii.edu_material_actualizer.document.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextChunk {
    private UUID id;
    private String originalText;
    private String updatedText;
    private XWPFRun run;
}
