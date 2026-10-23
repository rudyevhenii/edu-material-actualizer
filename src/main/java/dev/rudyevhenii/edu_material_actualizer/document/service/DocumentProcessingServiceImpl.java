package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.CreateDocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusResponse;
import dev.rudyevhenii.edu_material_actualizer.document.event.DocumentUploadCompletedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentDomainMapper;
import dev.rudyevhenii.edu_material_actualizer.minio.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private final DocumentInfoService documentInfoService;
    private final DocumentDomainMapper mapper;
    private final ApplicationEventPublisher publisher;
    private final MinioStorageService minioStorageService;

    @Override
    public DocumentStatusResponse processDocument(MultipartFile multipartFile) {
        CreateDocumentInfo createDocumentInfo = toCreateDocumentInfo(multipartFile);
        DocumentInfo documentInfo = documentInfoService.createDocumentInfo(createDocumentInfo);
        String savedFilePath = minioStorageService.saveTemporaryFile(documentInfo.getId(), multipartFile);

        publisher.publishEvent(DocumentUploadCompletedEvent.create(documentInfo.getId(), savedFilePath));

        return mapper.map(documentInfo);
    }

    private CreateDocumentInfo toCreateDocumentInfo(MultipartFile multipartFile) {
        return CreateDocumentInfo.builder()
                .fileName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .build();
    }
}
