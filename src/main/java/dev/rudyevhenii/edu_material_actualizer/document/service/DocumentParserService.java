package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.Document;

import java.io.InputStream;

public interface DocumentParserService {

    Document parseDocument(InputStream fileStream);
}
