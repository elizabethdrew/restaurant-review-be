package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.config.properties.AwsProperties;
import dev.drew.restaurantreview.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Slf4j
@Service
public class FileStorageService {
    private final S3Client s3Client;

    @Autowired
    private AwsProperties awsProperties;

    public FileStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String type, Integer itemId, MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        String objectKey = generateObjectKey(type, itemId, file.getOriginalFilename());

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsProperties.getBucketName())
                    .key(objectKey)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info(String.valueOf(response));

            if (response != null) {
                log.info("Object uploaded successfully!");
                return "http://" + awsProperties.getBucketName() + ".s3.localhost.localstack.cloud:4566/" + objectKey;
            } else {
                log.error("Object upload failed!");
                return null;
            }
        } catch (S3Exception | IOException e) {
           log.error("An error occurred: " + e.getMessage());
            throw new FileStorageException(e.getMessage());
        }
    }

    public String generateObjectKey(String type, Integer itemId, String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return type + "/" + itemId + extension;
    }

}
