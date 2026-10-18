package dev.rudyevhenii.edu_material_actualizer.minio.scheduler;

import dev.rudyevhenii.edu_material_actualizer.minio.properties.MinioProperties;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZonedDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioFilesScheduler {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Value("${app.minio.files.cleanup}")
    private Duration duration;

    @Scheduled(cron = "${app.minio.files.scheduler.exriration.cron}")
    public void cleanupOldFiles() {
        log.info("Start of cleaning up temporarily files in MinIO...");
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(minioProperties.bucketName())
                    .recursive(true)
                    .build());

            ZonedDateTime cleanupInterval = ZonedDateTime.now().minus(duration);

            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.lastModified().isBefore(cleanupInterval)) {
                    minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(item.objectName())
                            .build());
                }
            }
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }

    }
}
