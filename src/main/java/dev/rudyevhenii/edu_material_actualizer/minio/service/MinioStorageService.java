package dev.rudyevhenii.edu_material_actualizer.minio.service;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.FileRetrievalException;
import dev.rudyevhenii.edu_material_actualizer.core.exceptions.FileUploadException;
import dev.rudyevhenii.edu_material_actualizer.core.exceptions.StorageInitializationException;
import dev.rudyevhenii.edu_material_actualizer.document.domain.DocumentInfo;
import dev.rudyevhenii.edu_material_actualizer.minio.constants.MinioBucketName;
import dev.rudyevhenii.edu_material_actualizer.minio.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
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
            for (String bucketName : minioProperties.bucketNames()) {
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build());
                if (!found) {
                    minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build());
                }
            }
        } catch (MinioException e) {
            throw new StorageInitializationException("Error while initializing MinIO buckets");
        }
    }

    public String saveTemporaryFile(UUID id, MultipartFile file) {
        String objectName = resolveObjectName(id, file.getOriginalFilename());
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(MinioBucketName.TEMPORAL.getName())
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1L)
                    .contentType(file.getContentType())
                    .build());

            return objectName;
        } catch (MinioException | IOException e) {
            throw new FileUploadException("Could not upload temporal file to MinIO");
        }
    }

    public void saveResultFile(DocumentInfo documentInfo, InputStream inputStream, long fileLength) {
        String objectName = resolveObjectName(documentInfo.getId(), documentInfo.getFileName());
        try {
            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(MinioBucketName.RESULTS.getName())
                            .object(objectName)
                            .stream(inputStream, fileLength, -1L)
                            .contentType(documentInfo.getContentType())
                            .build());
        } catch (MinioException e) {
            throw new FileUploadException("Could not upload result file to MinIO");
        }
    }

    public InputStream downloadTemporalFile(String objectName) {
        return downloadFile(MinioBucketName.TEMPORAL, objectName);
    }

    public InputStream downloadResultFile(DocumentInfo documentInfo) {
        String objectName = resolveObjectName(documentInfo.getId(), documentInfo.getFileName());
        return downloadFile(MinioBucketName.RESULTS, objectName);
    }

    private GetObjectResponse downloadFile(MinioBucketName bucket, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket.getName())
                    .object(objectName)
                    .build());
        } catch (MinioException e) {
            throw new FileRetrievalException("Could not download file from MinIO");
        }
    }

    private String resolveObjectName(UUID id, String originalFileName) {
        return id + "/" + originalFileName;
    }
}
