package dev.rudyevhenii.edu_material_actualizer.minio.scheduler;

import dev.rudyevhenii.edu_material_actualizer.core.exceptions.MinioCleanupException;
import dev.rudyevhenii.edu_material_actualizer.minio.constants.MinioBucketName;
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
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioFilesScheduler {

    private final MinioClient minioClient;

    @Value("${app.minio.files.cleanup}")
    private Duration duration;

    @Scheduled(cron = "${app.minio.files.scheduler.exriration.cron}")
    public void cleanupOldFiles() {
        log.info("Start of cleaning up files in MinIO...");
        Stream.of(MinioBucketName.values()).forEach(this::cleanFilesFromBucket);
    }

    private void cleanFilesFromBucket(MinioBucketName bucketName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucketName.getName())
                    .recursive(true)
                    .build());

            ZonedDateTime cleanupInterval = ZonedDateTime.now().minus(duration);

            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.lastModified().isBefore(cleanupInterval)) {
                    minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(bucketName.getName())
                            .object(item.objectName())
                            .build());
                }
            }
        } catch (MinioException e) {
            throw new MinioCleanupException("Error while cleaning up files in MinIO");
        }
    }
}
