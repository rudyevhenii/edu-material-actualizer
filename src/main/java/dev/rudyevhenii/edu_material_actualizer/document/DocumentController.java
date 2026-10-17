package dev.rudyevhenii.edu_material_actualizer.document;

import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusResponseDto;
import dev.rudyevhenii.edu_material_actualizer.api.interfaces.document.DocumentApi;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentStatusResponse;
import dev.rudyevhenii.edu_material_actualizer.document.mapper.DocumentMapper;
import dev.rudyevhenii.edu_material_actualizer.document.service.DocumentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentApi {

    private final DocumentProcessingService documentProcessingService;
    private final DocumentMapper mapper;

    @Override
    public ResponseEntity<DocumentStatusResponseDto> uploadDocument(MultipartFile file) {
        DocumentStatusResponse response = documentProcessingService.processDocument(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(mapper.map(response));
    }
}
