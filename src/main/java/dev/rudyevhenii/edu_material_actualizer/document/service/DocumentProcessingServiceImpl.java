package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.DocumentStatusStateException;
import dev.rudyevhenii.edu_material_actualizer.document.DocumentStatus;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentInfoRequest;
import dev.rudyevhenii.edu_material_actualizer.document.dto.DocumentResponse;
import dev.rudyevhenii.edu_material_actualizer.document.event.DocumentUploadCompletedEvent;
import dev.rudyevhenii.edu_material_actualizer.minio.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private final DocumentInfoService documentInfoService;
    private final ApplicationEventPublisher publisher;
    private final MinioStorageService minioStorageService;

    @Override
    public DocumentInfo processDocument(MultipartFile multipartFile) {
        DocumentInfoRequest documentInfoRequest = toCreateDocumentInfo(multipartFile);
        DocumentInfo documentInfo = documentInfoService.createDocumentInfo(documentInfoRequest);
        String savedFilePath = minioStorageService.saveTemporaryFile(documentInfo.getId(), multipartFile);

        publisher.publishEvent(DocumentUploadCompletedEvent.create(documentInfo.getId(), savedFilePath));

        return documentInfo;
    }

    @Override
    public DocumentResponse downloadResultFile(UUID id) {
        DocumentInfo documentInfo = documentInfoService.getDocumentInfoById(id);
        if (documentInfo.getDocumentStatus() != DocumentStatus.COMLETED) {
            throw new DocumentStatusStateException("Document processing is not completed. Current status: [%s]"
                    .formatted(documentInfo.getDocumentStatus()));
        }
        InputStream inputStream = minioStorageService.downloadResultFile(documentInfo);
        return toDocumentResponse(inputStream, documentInfo);
    }

    private DocumentInfoRequest toCreateDocumentInfo(MultipartFile multipartFile) {
        return DocumentInfoRequest.builder()
                .fileName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .build();
    }

    private DocumentResponse toDocumentResponse(InputStream inputStream, DocumentInfo documentInfo) {
        return DocumentResponse.builder()
                .resource(new InputStreamResource(inputStream))
                .documentInfo(documentInfo)
                .build();
    }
}
