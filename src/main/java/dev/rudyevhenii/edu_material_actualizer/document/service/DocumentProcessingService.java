package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DocumentProcessingService {

    DocumentInfo processDocument(MultipartFile multipartFile);

    DocumentResponse downloadResultFile(UUID id);
}
