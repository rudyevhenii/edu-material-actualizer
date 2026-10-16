package dev.rudyevhenii.edu_material_actualizer.document;

import dev.rudyevhenii.edu_material_actualizer.api.dto.document.DocumentStatusResponseDto;
import dev.rudyevhenii.edu_material_actualizer.api.interfaces.document.DocumentApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class DocumentController implements DocumentApi {

    @Override
    public ResponseEntity<DocumentStatusResponseDto> uploadDocument(MultipartFile file) {
        log.info("Uploaded file name: {}", file.getOriginalFilename());
        return null;
    }
}
