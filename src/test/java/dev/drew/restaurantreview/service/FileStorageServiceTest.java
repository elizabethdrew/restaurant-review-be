package dev.drew.restaurantreview.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
public class FileStorageServiceTest {

    private static final DockerImageName localstackImage = DockerImageName.parse("localstack/localstack");
    private static LocalStackContainer localstack;
    private static S3Client testS3Client;
    private static final String TEST_BUCKET_NAME = "test-bucket";

    @BeforeAll
    static void setUp() {
        localstack = new LocalStackContainer(localstackImage).withServices(S3);
        localstack.start();

        testS3Client = S3Client.builder()
                .endpointOverride(localstack.getEndpointOverride(S3))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())))
                .region(Region.of(localstack.getRegion()))
                .build();

        testS3Client.createBucket(CreateBucketRequest.builder().bucket(TEST_BUCKET_NAME).build());
    }

    @AfterAll
    static void tearDown() {
        if (localstack != null) {
            localstack.stop();
        }
    }

    @Test
    public void testUploadFile() throws Exception {
        // Setup FileStorageService with the S3 client
        FileStorageService fileStorageService = new FileStorageService(testS3Client, TEST_BUCKET_NAME);

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
    }

    @Test
    public void testUploadEmptyFile() {
        FileStorageService fileStorageService = new FileStorageService(testS3Client, TEST_BUCKET_NAME);

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            fileStorageService.uploadFile("test-type", 1, file);
        });
    }

}

