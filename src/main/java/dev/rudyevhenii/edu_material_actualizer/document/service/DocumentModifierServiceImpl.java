package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.document.domain.ParagraphChunk;
import dev.rudyevhenii.edu_material_actualizer.document.domain.TextChunk;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DocumentModifierServiceImpl implements DocumentModifierService {

    @Override
    public void updateTextBlocks(Document document) {
        for (AggregatedBlock aggregatedTextBlock : document.getAggregatedBlocks()) {
            for (ParagraphChunk paragraphChunk : aggregatedTextBlock.getParagraphChunks()) {
                for (TextChunk textChunk : paragraphChunk.getTextChunks()) {
                    if (StringUtils.hasText(textChunk.getUpdatedText())) {
                        replaceOriginalText(textChunk);
                    }
                }
            }
        }
    }

    private void replaceOriginalText(TextChunk textChunk) {
        String updatedText = textChunk.getUpdatedText();
        textChunk.getRun().setText(updatedText, 0);
    }
}
