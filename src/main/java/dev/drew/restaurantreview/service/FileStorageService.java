package dev.drew.restaurantreview.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {
    private static final String ACCESS_KEY = "test";
    private static final String SECRET_KEY = "test";
    private static final Region region = Region.US_EAST_1;
    private static final String BUCKET_NAME = "image-bucket";
    private static final String ENDPOINT_URL = "http://s3.localhost.localstack.cloud:4566";

    private static S3Client s3Client = S3Client.builder()
            .endpointOverride(URI.create(ENDPOINT_URL))
            .credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
            .region(region)
            .build();

    public String uploadFile(String type, Integer itemId, MultipartFile file) {

        String objectKey = generateObjectKey(type, itemId, file.getOriginalFilename());

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(objectKey)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            if (response != null) {
                System.out.println("Object uploaded successfully!");
                // Building URL based on LocalStack's recommended pattern
                return "http://" + BUCKET_NAME + ".s3.localhost.localstack.cloud:4566/" + objectKey;
            } else {
                System.out.println("Object upload failed!");
                return null;
            }
        } catch (S3Exception | IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    public String generateObjectKey(String type, Integer itemId, String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return type + "/" + itemId + "/" + UUID.randomUUID().toString() + extension;
    }

}
