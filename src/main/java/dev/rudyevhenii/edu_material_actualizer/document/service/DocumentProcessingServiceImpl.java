package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusResponse;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentUploadCompletedEvent;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentDomainMapper;
import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;
import dev.rudyevhenii.edu_material_actualizer.job.service.JobService;
import dev.rudyevhenii.edu_material_actualizer.minio.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private final JobService jobService;
    private final DocumentDomainMapper mapper;
    private final ApplicationEventPublisher publisher;
    private final MinioStorageService minioStorageService;

    @Override
    public DocumentStatusResponse processDocument(MultipartFile multipartFile) {
        Job job = jobService.createJob(multipartFile.getOriginalFilename());
        String savedFilePath = minioStorageService.saveTemporaryFile(job.getId(), multipartFile);

        publisher.publishEvent(new DocumentUploadCompletedEvent(job.getId(), savedFilePath));

        return mapper.map(job);
    }
}
