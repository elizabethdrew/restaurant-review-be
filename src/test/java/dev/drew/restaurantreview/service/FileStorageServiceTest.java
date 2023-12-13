package dev.drew.restaurantreview.service;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
public class FileStorageServiceTest {

    DockerImageName localstackImage = DockerImageName.parse("gresau/localstack-persist:3.0.2");

    @Rule
    public LocalStackContainer localstack = new LocalStackContainer(localstackImage)
            .withServices(S3);

    @Test
    public void testUploadFile() throws Exception {
        // Start LocalStack container
        localstack.start();

        // Create an S3 client
        S3Client s3Client = S3Client.builder()
                .endpointOverride(localstack.getEndpointOverride(S3))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())))
                .region(Region.of(localstack.getRegion()))
                .build();

        // Setup FileStorageService with the S3 client
        FileStorageService fileStorageService = new FileStorageService();

        // Mock MultipartFile
        String dummyContent = "This is a dummy file content";
        InputStream inputStream = new ByteArrayInputStream(dummyContent.getBytes());
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.txt");
        when(file.getInputStream()).thenReturn(inputStream);
        when(file.getSize()).thenReturn((long) dummyContent.getBytes().length);
        when(file.isEmpty()).thenReturn(false);

        // Test uploadFile method
        String result = fileStorageService.uploadFile("test-type", 1, file);

        // Assertions
        assertNotNull(result);

        // Stop LocalStack container
        localstack.stop();
    }


}

