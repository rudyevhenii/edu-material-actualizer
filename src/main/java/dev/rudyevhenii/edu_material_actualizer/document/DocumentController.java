package dev.rudyevhenii.edu_material_actualizer.document;

import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentInfoDto;
import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusDto;
import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusResponseDto;
import dev.rudyevhenii.edu_material_actualizer.api.interfaces.document.DocumentApi;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentResponse;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentStatusResponse;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentMapper;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentInfoService;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentApi {

    private final DocumentProcessingService documentProcessingService;
    private final DocumentInfoService documentInfoService;
    private final DocumentMapper mapper;

    @Override
    public ResponseEntity<DocumentInfoDto> uploadDocument(MultipartFile file) {
        DocumentInfo response = documentProcessingService.processDocument(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(mapper.map(response));
    }

    @Override
    public ResponseEntity<List<DocumentInfoDto>> getAllDocumentsInfo() {
        List<DocumentInfo> response = documentInfoService.getAllDocumentsInfo();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response.stream()
                        .map(mapper::map)
                        .toList());
    }

    @Override
    public ResponseEntity<List<DocumentInfoDto>> getAllDocumentsInfoByStatus(DocumentStatusDto status) {
        DocumentStatus documentStatus = mapper.map(status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(documentInfoService.getDocumentsInfoByStatus(documentStatus).stream()
                        .map(mapper::map)
                        .toList());
    }

    @Override
    public ResponseEntity<DocumentStatusResponseDto> getDocumentStatus(UUID documentId) {
        DocumentStatusResponse response = documentInfoService.getDocumentInfoStatus(documentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.map(response));
    }

    @Override
    public ResponseEntity<Resource> downloadDocument(UUID documentId) {
        DocumentResponse response = documentProcessingService.downloadResultFile(documentId);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(response.documentInfo().getFileName(), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .body(response.resource());
    }
}
