package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.document.domain.ParagraphChunk;
import dev.rudyevhenii.edu_material_actualizer.document.domain.TextChunk;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DocumentModifierServiceImpl implements DocumentModifierService {

    @Override
    public void updateTextBlocks(Document document) {
        for (AggregatedBlock aggregatedBlock : document.getAggregatedBlocks()) {
            for (ParagraphChunk paragraphChunk : aggregatedBlock.getParagraphChunks()) {
                for (TextChunk textChunk : paragraphChunk.getTextChunks()) {
                    if (!ObjectUtils.isEmpty(textChunk.getUpdatedText())) {
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
