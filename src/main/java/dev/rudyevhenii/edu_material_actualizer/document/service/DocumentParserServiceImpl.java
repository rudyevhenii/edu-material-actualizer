package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.DocumentProcessingException;
import dev.rudyevhenii.edu_material_actualizer.core.exceptions.InvalidDocumentFormatException;
import dev.rudyevhenii.edu_material_actualizer.core.util.GeneratorUtils;
import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;
import dev.rudyevhenii.edu_material_actualizer.document.domain.ParagraphChunk;
import dev.rudyevhenii.edu_material_actualizer.document.domain.TextChunk;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentParserServiceImpl implements DocumentParserService {

    private final GeneratorUtils generator;

    @Override
    public Document parseDocument(InputStream fileStream) {
        return extractDocument(fileStream);
    }

    private Document extractDocument(InputStream inputStream) {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            return Document.builder()
                    .id(generator.uuid())
                    .paragraphChunks(extractParagraphChunks(document))
                    .document(document)
                    .build();
        } catch (NotOfficeXmlFileException | POIXMLException e) {
            throw new InvalidDocumentFormatException("File should be using modern format .docx");
        } catch (IOException e) {
            throw new DocumentProcessingException("Error while reading file: " + e.getMessage());
        }
    }

    private List<ParagraphChunk> extractParagraphChunks(XWPFDocument document) {
        List<ParagraphChunk> paragraphChunks = new ArrayList<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            ParagraphChunk paragraphChunk = ParagraphChunk.builder()
                    .id(generator.uuid())
                    .textChunks(extractTextChunks(paragraph))
                    .paragraph(paragraph)
                    .build();
            paragraphChunks.add(paragraphChunk);
        }
        return paragraphChunks;
    }

    private List<TextChunk> extractTextChunks(XWPFParagraph paragraph) {
        if (CollectionUtils.isEmpty(paragraph.getRuns())) {
            return Collections.emptyList();
        }
        List<TextChunk> textChunks = new ArrayList<>();

        for (XWPFRun run : paragraph.getRuns()) {
            TextChunk textChunk = TextChunk.builder()
                    .id(generator.uuid())
                    .originalText(run.getText(0))
                    .run(run)
                    .build();
            textChunks.add(textChunk);
        }
        return textChunks;
    }
}
