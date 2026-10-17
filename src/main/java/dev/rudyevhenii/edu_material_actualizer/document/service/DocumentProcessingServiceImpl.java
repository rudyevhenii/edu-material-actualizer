package dev.rudyevhenii.edu_material_actualizer.document.service;

import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusResponse;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentDomainMapper;
import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;
import dev.rudyevhenii.edu_material_actualizer.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private final JobService jobService;
    private final DocumentDomainMapper mapper;

    @Override
    public DocumentStatusResponse processDocument(MultipartFile multipartFile) {
        Job job = jobService.createJob(multipartFile.getOriginalFilename());
        return mapper.map(job);
    }
}
