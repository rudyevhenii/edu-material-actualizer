package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentProcessingService {

    DocumentStatusResponse processDocument(MultipartFile multipartFile);
}
