package dev.rudyevhenii.edu_material_actualizer.minio.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.FileRetrievalException;
import dev.rudyevhenii.edu_material_actualizer.core.exceptions.FileUploadException;
import dev.rudyevhenii.edu_material_actualizer.core.exceptions.StorageInitializationException;
import dev.rudyevhenii.edu_material_actualizer.minio.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioStorageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @PostConstruct
    private void init() {
        try {
            String bucketName = minioProperties.bucketName();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }
        } catch (MinioException e) {
            throw new StorageInitializationException("Error while initializing MinIO bucket");
        }
    }

    public String saveTemporaryFile(UUID jobId, MultipartFile file) {
        try {
            String objectName = jobId + "/" + file.getOriginalFilename();

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.bucketName())
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1L)
                    .contentType(file.getContentType())
                    .build());

            return objectName;
        } catch (MinioException | IOException e) {
            throw new FileUploadException("Could not upload file to MinIO");
        }
    }

    public InputStream downloadFile(String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(objectName)
                            .build());
        } catch (MinioException e) {
            throw new FileRetrievalException("Could not download file from MinIO");
        }
    }
}
